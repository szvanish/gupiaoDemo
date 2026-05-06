from fastapi import APIRouter, HTTPException, Request, Query
import asyncio
import pandas as pd
from config import settings
from services.cache import CacheService
from services.a_share import AShareService
from services.hk_stock import HKStockService
from services.us_stock import USStockService
from services.indicators import calculate_indicators
from services.fundamental import score_fundamental, compute_valuation
from services.ai_report import AIReportService
from models.stock import (StockAnalysis, FundamentalData, ValuationData,
                          SentimentData, ChipData, MacroData)

router = APIRouter()

def _get_service(market: str):
    if market == "A":
        return AShareService()
    elif market == "HK":
        return HKStockService()
    elif market == "US":
        return USStockService()
    raise HTTPException(status_code=400, detail="market must be A, HK or US")

async def get_cache(request: Request, key: str):
    cache = CacheService(request.app.state.redis)
    return await cache.get(key)

async def set_cache(request: Request, key: str, data, ttl: int):
    cache = CacheService(request.app.state.redis)
    await cache.set(key, data, ttl)

@router.get("/search")
async def search_stock(q: str = Query(..., min_length=1), market: str = Query("A")):
    svc = _get_service(market)
    return svc.search(q)

@router.get("/{code}/quote")
async def get_quote(code: str, market: str, request: Request):
    cache_key = f"quote:{market}:{code}"
    cached = await get_cache(request, cache_key)
    if cached:
        return cached
    svc = _get_service(market)
    try:
        quote = await asyncio.to_thread(svc.get_quote, code)
    except ValueError as e:
        raise HTTPException(status_code=404, detail=str(e))
    data = quote.model_dump()
    await set_cache(request, cache_key, data, settings.cache_ttl_quote)
    return data

@router.get("/{code}/kline")
async def get_kline(code: str, market: str, request: Request, period: str = "day"):
    cache_key = f"kline:{market}:{code}:{period}"
    cached = await get_cache(request, cache_key)
    if cached:
        return cached
    svc = _get_service(market)
    bars = await asyncio.to_thread(svc.get_kline, code, period)
    data = [b.model_dump() for b in bars]
    await set_cache(request, cache_key, data, settings.cache_ttl_kline)
    return data

async def _noop(default):
    return default

@router.get("/{code}/analysis")
async def get_analysis(code: str, market: str, request: Request):
    cache_key = f"analysis:{market}:{code}"
    cached = await get_cache(request, cache_key)
    if cached:
        return cached
    svc = _get_service(market)
    try:
        quote, bars, fundamental, sentiment = await asyncio.gather(
            asyncio.to_thread(svc.get_quote, code),
            asyncio.to_thread(svc.get_kline, code, "day"),
            asyncio.to_thread(svc.get_fundamental, code) if hasattr(svc, "get_fundamental") else _noop(FundamentalData()),
            asyncio.to_thread(svc.get_sentiment, code) if hasattr(svc, "get_sentiment") else _noop(SentimentData()),
        )
        df = pd.DataFrame([b.model_dump() for b in bars])
        technical = calculate_indicators(df)
        fundamental.score = score_fundamental(fundamental)
        valuation = compute_valuation(fundamental, industry_avg_pe=None, industry_avg_pb=None)
        analysis = StockAnalysis(
            code=code, name=quote.name, market=market,
            technical=technical, fundamental=fundamental,
            valuation=valuation, sentiment=sentiment,
            chip=ChipData(), macro=MacroData(),
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    data = analysis.model_dump()
    await set_cache(request, cache_key, data, settings.cache_ttl_kline)
    return data

@router.get("/{code}/report")
async def get_report(code: str, market: str, request: Request):
    cache_key = f"report:{market}:{code}"
    cached = await get_cache(request, cache_key)
    if cached:
        cached["cached"] = True
        return cached
    analysis_data = await get_analysis(code, market, request)
    analysis = StockAnalysis(**analysis_data)
    svc = AIReportService(api_key=settings.deepseek_api_key)
    try:
        report = svc.generate(analysis)
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"AI report generation failed: {e}")
    data = report.model_dump()
    await set_cache(request, cache_key, data, settings.cache_ttl_report)
    return data
