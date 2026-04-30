from fastapi import APIRouter, Request, Query
from services.news_service import NewsService
from services.cache import CacheService
from config import settings

router = APIRouter()

async def get_cache(request: Request, key: str):
    cache = CacheService(request.app.state.redis)
    return await cache.get(key)

async def set_cache(request: Request, key: str, data, ttl: int):
    cache = CacheService(request.app.state.redis)
    await cache.set(key, data, ttl)

@router.get("/hot")
async def get_hot_news(request: Request, market: str = "all", page: int = 1):
    cache_key = f"news:hot:{market}:{page}"
    cached = await get_cache(request, cache_key)
    if cached:
        return cached
    svc = NewsService()
    items = svc.get_hot_news(market=market, page=page)
    data = [i.model_dump() for i in items]
    await set_cache(request, cache_key, data, settings.cache_ttl_news)
    return data

@router.get("/stock/{code}")
async def get_stock_news(code: str, request: Request, market: str = Query("A"), page: int = 1):
    cache_key = f"news:stock:{market}:{code}:{page}"
    cached = await get_cache(request, cache_key)
    if cached:
        return cached
    svc = NewsService()
    items = svc.get_stock_news(code=code, market=market, page=page)
    data = [i.model_dump() for i in items]
    await set_cache(request, cache_key, data, settings.cache_ttl_news)
    return data
