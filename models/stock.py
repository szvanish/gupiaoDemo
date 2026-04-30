from pydantic import BaseModel
from typing import Optional

class StockSearchResult(BaseModel):
    code: str
    name: str
    market: str  # A | HK | US
    price: Optional[float] = None
    change_pct: Optional[float] = None

class StockQuote(BaseModel):
    code: str
    name: str
    market: str
    price: float
    change: float
    change_pct: float
    volume: float
    amount: float
    high: float
    low: float
    open: float
    prev_close: float

class KLineBar(BaseModel):
    date: str
    open: float
    high: float
    low: float
    close: float
    volume: float

class TechnicalIndicators(BaseModel):
    ma5: Optional[float] = None
    ma10: Optional[float] = None
    ma20: Optional[float] = None
    ma60: Optional[float] = None
    macd_dif: Optional[float] = None
    macd_dea: Optional[float] = None
    macd_bar: Optional[float] = None
    rsi6: Optional[float] = None
    rsi12: Optional[float] = None
    rsi24: Optional[float] = None
    kdj_k: Optional[float] = None
    kdj_d: Optional[float] = None
    kdj_j: Optional[float] = None
    boll_upper: Optional[float] = None
    boll_mid: Optional[float] = None
    boll_lower: Optional[float] = None
    volume_ratio: Optional[float] = None
    turnover_rate: Optional[float] = None
    score: int = 50

class FundamentalData(BaseModel):
    pe_ttm: Optional[float] = None
    pb: Optional[float] = None
    ps: Optional[float] = None
    roe: Optional[float] = None
    roa: Optional[float] = None
    revenue_growth_yoy: Optional[float] = None
    profit_growth_yoy: Optional[float] = None
    gross_margin: Optional[float] = None
    net_margin: Optional[float] = None
    debt_ratio: Optional[float] = None
    current_ratio: Optional[float] = None
    free_cash_flow: Optional[float] = None
    eps: Optional[float] = None
    dividend_yield: Optional[float] = None
    score: int = 50

class ValuationData(BaseModel):
    industry_avg_pe: Optional[float] = None
    industry_avg_pb: Optional[float] = None
    pe_percentile: Optional[float] = None
    pb_percentile: Optional[float] = None
    peg: Optional[float] = None
    score: int = 50

class SentimentData(BaseModel):
    main_net_inflow: Optional[float] = None
    super_large_net: Optional[float] = None
    large_net: Optional[float] = None
    northbound_net: Optional[float] = None
    margin_balance: Optional[float] = None
    news_heat_score: int = 50
    is_limit_up: bool = False
    is_limit_down: bool = False
    score: int = 50

class ChipData(BaseModel):
    institution_holding_pct: Optional[float] = None
    shareholder_count_change_pct: Optional[float] = None
    top10_holder_change: Optional[str] = None
    score: int = 50

class MacroData(BaseModel):
    industry_name: Optional[str] = None
    industry_change_pct: Optional[float] = None
    corr_with_index: Optional[float] = None
    policy_summary: Optional[str] = None
    score: int = 50

class StockAnalysis(BaseModel):
    code: str
    name: str
    market: str
    technical: TechnicalIndicators
    fundamental: FundamentalData
    valuation: ValuationData
    sentiment: SentimentData
    chip: ChipData
    macro: MacroData

class AIReport(BaseModel):
    code: str
    name: str
    market: str
    summary: str
    market_review: str
    fundamental_analysis: str
    technical_analysis: str
    valuation_analysis: str
    sentiment_analysis: str
    risks: list[str]
    recommendation: str
    rating: str
    target_price_low: Optional[float] = None
    target_price_high: Optional[float] = None
    generated_at: str
    cached: bool = False
