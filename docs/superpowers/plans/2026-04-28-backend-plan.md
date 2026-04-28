# 股票分析 Backend (FastAPI) 实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 构建 Python FastAPI 后端，抓取 A股/港股/美股数据，计算六大分析维度，通过 Claude API 生成券商研报风格的 AI 分析报告，并用 Redis 缓存全部结果。

**Architecture:** 三层服务：数据抓取层（akshare 负责 A股/港股，yfinance 负责美股），指标计算层（六维分析），AI 报告层（Claude API）。FastAPI 路由层将三层整合为 REST API 对外暴露，Redis 缓存所有耗时结果。

**Tech Stack:** Python 3.11+, FastAPI, Uvicorn, Redis (redis-py asyncio), akshare, yfinance, pandas, pandas-ta, anthropic SDK, pytest, pytest-asyncio

---

## 文件结构

```
backend/
├── main.py                      # FastAPI 应用入口、lifespan、路由注册
├── config.py                    # 环境变量与缓存 TTL 配置
├── requirements.txt
├── .env.example
├── models/
│   ├── __init__.py
│   ├── stock.py                 # 股票相关 Pydantic 模型
│   └── news.py                  # 新闻 Pydantic 模型
├── services/
│   ├── __init__.py
│   ├── cache.py                 # Redis 缓存封装
│   ├── us_stock.py              # 美股数据 (yfinance)
│   ├── a_share.py               # A股数据 (akshare)
│   ├── hk_stock.py              # 港股数据 (akshare)
│   ├── indicators.py            # 技术指标计算 (pandas-ta)
│   ├── fundamental.py           # 基本面 + 估值分析
│   ├── sentiment.py             # 情绪面 + 筹码分析
│   ├── news_service.py          # 新闻抓取 (akshare)
│   └── ai_report.py             # Claude API 研报生成
├── routers/
│   ├── __init__.py
│   ├── stock.py                 # /stock/* 端点
│   └── news.py                  # /news/* 端点
└── tests/
    ├── __init__.py
    ├── test_cache.py
    ├── test_indicators.py
    ├── test_fundamental.py
    ├── test_ai_report.py
    ├── test_stock_router.py
    └── test_news_router.py
```

---

### Task 1: 项目初始化

**Files:**
- Create: `backend/requirements.txt`
- Create: `backend/config.py`
- Create: `backend/.env.example`
- Create: `backend/main.py`
- Create: `backend/routers/stock.py`
- Create: `backend/routers/news.py`
- Create: `backend/models/__init__.py`
- Create: `backend/services/__init__.py`
- Create: `backend/routers/__init__.py`
- Create: `backend/tests/__init__.py`

- [ ] **Step 1: 创建目录结构**

```bash
cd D:/aiproject/gupiao
mkdir -p backend/{models,services,routers,tests}
touch backend/{models,services,routers,tests}/__init__.py
```

- [ ] **Step 2: 创建 requirements.txt**

```
fastapi==0.111.0
uvicorn[standard]==0.29.0
redis==5.0.4
pydantic-settings==2.2.1
akshare==1.14.1
yfinance==0.2.38
pandas==2.2.2
pandas-ta==0.3.14b0
anthropic==0.28.0
httpx==0.27.0
python-dotenv==1.0.1
pytest==8.2.0
pytest-asyncio==0.23.6
pytest-mock==3.14.0
```

- [ ] **Step 3: 创建 .env.example**

```
CLAUDE_API_KEY=your_claude_api_key_here
REDIS_URL=redis://localhost:6379
```

- [ ] **Step 4: 创建 config.py**

```python
from pydantic_settings import BaseSettings

class Settings(BaseSettings):
    redis_url: str = "redis://localhost:6379"
    claude_api_key: str = ""
    cache_ttl_quote: int = 300
    cache_ttl_financials: int = 86400
    cache_ttl_news: int = 1800
    cache_ttl_report: int = 7200

    class Config:
        env_file = ".env"

settings = Settings()
```

- [ ] **Step 5: 创建空路由占位**

`backend/routers/stock.py`:
```python
from fastapi import APIRouter
router = APIRouter()
```

`backend/routers/news.py`:
```python
from fastapi import APIRouter
router = APIRouter()
```

- [ ] **Step 6: 创建 main.py**

```python
from fastapi import FastAPI
from contextlib import asynccontextmanager
import redis.asyncio as aioredis
from routers import stock, news
from config import settings

@asynccontextmanager
async def lifespan(app: FastAPI):
    app.state.redis = aioredis.from_url(settings.redis_url, decode_responses=True)
    yield
    await app.state.redis.aclose()

app = FastAPI(title="Stock Analyzer API", version="1.0.0", lifespan=lifespan)
app.include_router(stock.router, prefix="/stock", tags=["stock"])
app.include_router(news.router, prefix="/news", tags=["news"])

@app.get("/health")
async def health():
    return {"status": "ok"}
```

- [ ] **Step 7: 安装依赖并验证服务启动**

```bash
cd backend
python -m venv venv
# Windows:
venv\Scripts\activate
pip install -r requirements.txt
cp .env.example .env
# 编辑 .env 填入 CLAUDE_API_KEY
uvicorn main:app --reload --host 0.0.0.0 --port 8000
```

访问 `http://localhost:8000/health`，预期返回 `{"status":"ok"}`
访问 `http://localhost:8000/docs` 可见 Swagger UI

- [ ] **Step 8: Commit**

```bash
git init
git add backend/
git commit -m "feat: backend project skeleton with FastAPI + health endpoint"
```

---

### Task 2: Pydantic 数据模型

**Files:**
- Create: `backend/models/stock.py`
- Create: `backend/models/news.py`

- [ ] **Step 1: 创建 models/stock.py**

```python
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
```

- [ ] **Step 2: 创建 models/news.py**

```python
from pydantic import BaseModel
from typing import Optional

class NewsItem(BaseModel):
    id: str
    title: str
    source: str
    published_at: str
    url: str
    related_stocks: list[str] = []
    summary: Optional[str] = None
```

- [ ] **Step 3: Commit**

```bash
git add backend/models/
git commit -m "feat: add Pydantic data models for stock and news"
```

---

### Task 3: Redis 缓存服务

**Files:**
- Create: `backend/services/cache.py`
- Create: `backend/tests/test_cache.py`

- [ ] **Step 1: 编写失败测试**

`backend/tests/test_cache.py`:
```python
import pytest
import json
from unittest.mock import AsyncMock, MagicMock
from services.cache import CacheService

@pytest.fixture
def mock_redis():
    redis = AsyncMock()
    redis.get = AsyncMock(return_value=None)
    redis.setex = AsyncMock()
    return redis

@pytest.mark.asyncio
async def test_get_returns_none_on_miss(mock_redis):
    cache = CacheService(mock_redis)
    result = await cache.get("missing_key")
    assert result is None

@pytest.mark.asyncio
async def test_set_and_get(mock_redis):
    data = {"price": 100.5, "name": "腾讯"}
    mock_redis.get = AsyncMock(return_value=json.dumps(data))
    cache = CacheService(mock_redis)
    await cache.set("test_key", data, ttl=300)
    result = await cache.get("test_key")
    assert result == data

@pytest.mark.asyncio
async def test_set_calls_setex_with_correct_ttl(mock_redis):
    cache = CacheService(mock_redis)
    await cache.set("key", {"val": 1}, ttl=600)
    mock_redis.setex.assert_called_once_with("key", 600, json.dumps({"val": 1}))
```

- [ ] **Step 2: 运行测试，确认失败**

```bash
cd backend
pytest tests/test_cache.py -v
```

预期：`ModuleNotFoundError: No module named 'services.cache'`

- [ ] **Step 3: 实现 services/cache.py**

```python
import json
from typing import Any, Optional

class CacheService:
    def __init__(self, redis):
        self.redis = redis

    async def get(self, key: str) -> Optional[Any]:
        value = await self.redis.get(key)
        if value is None:
            return None
        return json.loads(value)

    async def set(self, key: str, data: Any, ttl: int) -> None:
        await self.redis.setex(key, ttl, json.dumps(data))

    async def delete(self, key: str) -> None:
        await self.redis.delete(key)
```

- [ ] **Step 4: 运行测试，确认通过**

```bash
pytest tests/test_cache.py -v
```

预期：3 passed

- [ ] **Step 5: Commit**

```bash
git add backend/services/cache.py backend/tests/test_cache.py
git commit -m "feat: Redis cache service with get/set/delete"
```

---

### Task 4: 技术指标计算服务

**Files:**
- Create: `backend/services/indicators.py`
- Create: `backend/tests/test_indicators.py`

- [ ] **Step 1: 编写失败测试**

`backend/tests/test_indicators.py`:
```python
import pytest
import pandas as pd
from services.indicators import calculate_indicators
from models.stock import TechnicalIndicators

def make_ohlcv(n=100):
    import numpy as np
    np.random.seed(42)
    close = 10 + np.cumsum(np.random.randn(n) * 0.1)
    df = pd.DataFrame({
        "date": pd.date_range("2024-01-01", periods=n).strftime("%Y-%m-%d"),
        "open": close * 0.99,
        "high": close * 1.01,
        "low": close * 0.98,
        "close": close,
        "volume": np.random.randint(1000000, 5000000, n).astype(float),
    })
    return df

def test_returns_technical_indicators_model():
    df = make_ohlcv(100)
    result = calculate_indicators(df)
    assert isinstance(result, TechnicalIndicators)

def test_ma5_is_close_to_recent_average():
    df = make_ohlcv(100)
    result = calculate_indicators(df)
    expected_ma5 = df["close"].iloc[-5:].mean()
    assert result.ma5 is not None
    assert abs(result.ma5 - expected_ma5) < 0.01

def test_score_is_between_0_and_100():
    df = make_ohlcv(100)
    result = calculate_indicators(df)
    assert 0 <= result.score <= 100

def test_macd_fields_present():
    df = make_ohlcv(100)
    result = calculate_indicators(df)
    assert result.macd_dif is not None
    assert result.macd_dea is not None
    assert result.macd_bar is not None
```

- [ ] **Step 2: 运行测试，确认失败**

```bash
pytest tests/test_indicators.py -v
```

预期：`ModuleNotFoundError: No module named 'services.indicators'`

- [ ] **Step 3: 实现 services/indicators.py**

```python
import pandas as pd
import pandas_ta as ta
from models.stock import TechnicalIndicators

def calculate_indicators(df: pd.DataFrame) -> TechnicalIndicators:
    """
    df columns: date, open, high, low, close, volume (float)
    Returns TechnicalIndicators with latest values and a 0-100 score.
    """
    close = df["close"]
    volume = df["volume"]

    ma5 = close.rolling(5).mean().iloc[-1]
    ma10 = close.rolling(10).mean().iloc[-1]
    ma20 = close.rolling(20).mean().iloc[-1]
    ma60 = close.rolling(60).mean().iloc[-1] if len(df) >= 60 else None

    macd = ta.macd(close)
    macd_dif = macd["MACD_12_26_9"].iloc[-1] if macd is not None else None
    macd_dea = macd["MACDs_12_26_9"].iloc[-1] if macd is not None else None
    macd_bar = macd["MACDh_12_26_9"].iloc[-1] if macd is not None else None

    rsi = ta.rsi(close, length=6)
    rsi6 = rsi.iloc[-1] if rsi is not None else None
    rsi12 = ta.rsi(close, length=12).iloc[-1] if ta.rsi(close, length=12) is not None else None
    rsi24 = ta.rsi(close, length=24).iloc[-1] if len(df) >= 24 else None

    stoch = ta.stoch(df["high"], df["low"], close)
    kdj_k = stoch["STOCHk_14_3_3"].iloc[-1] if stoch is not None else None
    kdj_d = stoch["STOCHd_14_3_3"].iloc[-1] if stoch is not None else None
    kdj_j = 3 * kdj_k - 2 * kdj_d if kdj_k is not None and kdj_d is not None else None

    bbands = ta.bbands(close)
    boll_upper = bbands["BBU_5_2.0"].iloc[-1] if bbands is not None else None
    boll_mid = bbands["BBM_5_2.0"].iloc[-1] if bbands is not None else None
    boll_lower = bbands["BBL_5_2.0"].iloc[-1] if bbands is not None else None

    avg_volume_5 = volume.rolling(5).mean().iloc[-1]
    volume_ratio = volume.iloc[-1] / avg_volume_5 if avg_volume_5 > 0 else None

    score = _score_technical(close.iloc[-1], ma5, ma20, macd_bar, rsi6)

    return TechnicalIndicators(
        ma5=_r(ma5), ma10=_r(ma10), ma20=_r(ma20), ma60=_r(ma60),
        macd_dif=_r(macd_dif), macd_dea=_r(macd_dea), macd_bar=_r(macd_bar),
        rsi6=_r(rsi6), rsi12=_r(rsi12), rsi24=_r(rsi24),
        kdj_k=_r(kdj_k), kdj_d=_r(kdj_d), kdj_j=_r(kdj_j),
        boll_upper=_r(boll_upper), boll_mid=_r(boll_mid), boll_lower=_r(boll_lower),
        volume_ratio=_r(volume_ratio),
        score=score,
    )

def _r(val) -> float | None:
    if val is None:
        return None
    try:
        import math
        if math.isnan(float(val)):
            return None
        return round(float(val), 4)
    except Exception:
        return None

def _score_technical(price, ma5, ma20, macd_bar, rsi6) -> int:
    score = 50
    try:
        if price and ma5 and price > ma5:
            score += 10
        if price and ma20 and price > ma20:
            score += 10
        if macd_bar and macd_bar > 0:
            score += 10
        if rsi6:
            if 40 < rsi6 < 70:
                score += 10
            elif rsi6 < 30:
                score += 15
            elif rsi6 > 80:
                score -= 15
    except Exception:
        pass
    return max(0, min(100, score))
```

- [ ] **Step 4: 运行测试，确认通过**

```bash
pytest tests/test_indicators.py -v
```

预期：4 passed

- [ ] **Step 5: Commit**

```bash
git add backend/services/indicators.py backend/tests/test_indicators.py
git commit -m "feat: technical indicator calculator with pandas-ta"
```

---

### Task 5: 美股数据服务 (yfinance)

**Files:**
- Create: `backend/services/us_stock.py`

- [ ] **Step 1: 编写测试（mock yfinance 避免网络依赖）**

```python
# backend/tests/test_us_stock.py
import pytest
from unittest.mock import patch, MagicMock
import pandas as pd
import numpy as np
from services.us_stock import USStockService
from models.stock import StockQuote, KLineBar, StockSearchResult

def make_mock_ticker(price=150.0, prev_close=148.0):
    ticker = MagicMock()
    ticker.info = {
        "shortName": "Apple Inc.",
        "currentPrice": price,
        "previousClose": prev_close,
        "volume": 80000000,
        "dayHigh": 151.0,
        "dayLow": 149.0,
        "open": 148.5,
        "regularMarketPreviousClose": prev_close,
        "marketCap": 2000000000000,
        "trailingPE": 28.5,
        "priceToBook": 45.0,
        "returnOnEquity": 1.47,
        "revenueGrowth": 0.05,
        "grossMargins": 0.44,
        "debtToEquity": 180.0,
        "trailingEps": 6.43,
        "dividendYield": 0.005,
        "sector": "Technology",
        "industry": "Consumer Electronics",
    }
    n = 120
    close = 140 + np.cumsum(np.random.randn(n) * 0.5)
    idx = pd.date_range("2023-09-01", periods=n, freq="B")
    ticker.history = MagicMock(return_value=pd.DataFrame({
        "Open": close * 0.99,
        "High": close * 1.01,
        "Low": close * 0.98,
        "Close": close,
        "Volume": np.random.randint(50000000, 100000000, n).astype(float),
    }, index=idx))
    return ticker

@patch("services.us_stock.yf.Ticker")
def test_get_quote(mock_ticker_cls):
    mock_ticker_cls.return_value = make_mock_ticker()
    svc = USStockService()
    result = svc.get_quote("AAPL")
    assert isinstance(result, StockQuote)
    assert result.code == "AAPL"
    assert result.market == "US"
    assert result.price == 150.0

@patch("services.us_stock.yf.Ticker")
def test_get_kline_returns_list_of_bars(mock_ticker_cls):
    mock_ticker_cls.return_value = make_mock_ticker()
    svc = USStockService()
    result = svc.get_kline("AAPL", period="day")
    assert isinstance(result, list)
    assert len(result) > 0
    assert isinstance(result[0], KLineBar)

@patch("services.us_stock.yf.search")
def test_search(mock_search):
    mock_search.return_value = {"quotes": [{"symbol": "AAPL", "longname": "Apple Inc.", "regularMarketPrice": 150.0}]}
    svc = USStockService()
    results = svc.search("Apple")
    assert len(results) > 0
    assert results[0].code == "AAPL"
```

- [ ] **Step 2: 运行测试，确认失败**

```bash
pytest tests/test_us_stock.py -v
```

- [ ] **Step 3: 实现 services/us_stock.py**

```python
import yfinance as yf
import pandas as pd
from models.stock import StockQuote, KLineBar, StockSearchResult, FundamentalData

PERIOD_MAP = {"day": "6mo", "week": "2y", "month": "5y"}
INTERVAL_MAP = {"day": "1d", "week": "1wk", "month": "1mo"}

class USStockService:
    def get_quote(self, code: str) -> StockQuote:
        ticker = yf.Ticker(code)
        info = ticker.info
        price = info.get("currentPrice") or info.get("regularMarketPrice", 0)
        prev_close = info.get("previousClose") or info.get("regularMarketPreviousClose", price)
        change = price - prev_close
        change_pct = (change / prev_close * 100) if prev_close else 0
        return StockQuote(
            code=code,
            name=info.get("shortName", code),
            market="US",
            price=round(price, 4),
            change=round(change, 4),
            change_pct=round(change_pct, 2),
            volume=info.get("volume", 0),
            amount=info.get("volume", 0) * price,
            high=info.get("dayHigh", price),
            low=info.get("dayLow", price),
            open=info.get("open", price),
            prev_close=round(prev_close, 4),
        )

    def get_kline(self, code: str, period: str = "day") -> list[KLineBar]:
        ticker = yf.Ticker(code)
        hist = ticker.history(
            period=PERIOD_MAP.get(period, "6mo"),
            interval=INTERVAL_MAP.get(period, "1d"),
        )
        return [
            KLineBar(
                date=str(idx.date()),
                open=round(row["Open"], 4),
                high=round(row["High"], 4),
                low=round(row["Low"], 4),
                close=round(row["Close"], 4),
                volume=float(row["Volume"]),
            )
            for idx, row in hist.iterrows()
        ]

    def get_fundamental(self, code: str) -> FundamentalData:
        info = yf.Ticker(code).info
        roe = info.get("returnOnEquity")
        return FundamentalData(
            pe_ttm=info.get("trailingPE"),
            pb=info.get("priceToBook"),
            roe=round(roe * 100, 2) if roe else None,
            revenue_growth_yoy=round(info.get("revenueGrowth", 0) * 100, 2),
            gross_margin=round(info.get("grossMargins", 0) * 100, 2),
            debt_ratio=info.get("debtToEquity"),
            eps=info.get("trailingEps"),
            dividend_yield=round(info.get("dividendYield", 0) * 100, 2) if info.get("dividendYield") else None,
            score=_score_fundamental(info),
        )

    def search(self, query: str) -> list[StockSearchResult]:
        try:
            results = yf.search(query)
            quotes = results.get("quotes", [])
            return [
                StockSearchResult(
                    code=q["symbol"],
                    name=q.get("longname") or q.get("shortname", q["symbol"]),
                    market="US",
                    price=q.get("regularMarketPrice"),
                )
                for q in quotes if q.get("symbol")
            ][:10]
        except Exception:
            return []

def _score_fundamental(info: dict) -> int:
    score = 50
    pe = info.get("trailingPE")
    roe = info.get("returnOnEquity")
    if pe and 0 < pe < 25:
        score += 10
    elif pe and pe > 50:
        score -= 10
    if roe and roe > 0.15:
        score += 15
    if info.get("revenueGrowth", 0) > 0.1:
        score += 10
    return max(0, min(100, score))
```

- [ ] **Step 4: 运行测试，确认通过**

```bash
pytest tests/test_us_stock.py -v
```

- [ ] **Step 5: Commit**

```bash
git add backend/services/us_stock.py backend/tests/test_us_stock.py
git commit -m "feat: US stock data service via yfinance"
```

---

### Task 6: A股数据服务 (akshare)

**Files:**
- Create: `backend/services/a_share.py`

- [ ] **Step 1: 编写测试（mock akshare）**

`backend/tests/test_a_share.py`:
```python
import pytest
import pandas as pd
from unittest.mock import patch, MagicMock
from services.a_share import AShareService
from models.stock import StockQuote, KLineBar, StockSearchResult

def make_spot_df(code="600036"):
    return pd.DataFrame([{
        "代码": code, "名称": "招商银行",
        "最新价": 42.5, "涨跌幅": 1.23, "涨跌额": 0.51,
        "成交量": 50000000, "成交额": 2125000000,
        "最高": 43.0, "最低": 42.0, "今开": 42.1,
        "昨收": 41.99,
    }])

def make_hist_df():
    return pd.DataFrame({
        "日期": ["2024-01-02", "2024-01-03"],
        "开盘": [42.0, 42.1],
        "最高": [43.0, 43.2],
        "最低": [41.8, 41.9],
        "收盘": [42.5, 42.8],
        "成交量": [50000000, 48000000],
    })

@patch("services.a_share.ak.stock_zh_a_spot_em")
def test_get_quote(mock_spot):
    mock_spot.return_value = make_spot_df("600036")
    svc = AShareService()
    result = svc.get_quote("600036")
    assert isinstance(result, StockQuote)
    assert result.market == "A"
    assert result.price == 42.5
    assert result.name == "招商银行"

@patch("services.a_share.ak.stock_zh_a_hist")
def test_get_kline(mock_hist):
    mock_hist.return_value = make_hist_df()
    svc = AShareService()
    result = svc.get_kline("600036", period="day")
    assert isinstance(result, list)
    assert len(result) == 2
    assert isinstance(result[0], KLineBar)

@patch("services.a_share.ak.stock_info_a_code_name")
def test_search(mock_name):
    mock_name.return_value = pd.DataFrame({"code": ["600036", "601398"], "name": ["招商银行", "工商银行"]})
    svc = AShareService()
    results = svc.search("招商")
    assert len(results) >= 1
    assert results[0].code == "600036"
```

- [ ] **Step 2: 运行测试，确认失败**

```bash
pytest tests/test_a_share.py -v
```

- [ ] **Step 3: 实现 services/a_share.py**

```python
import akshare as ak
import pandas as pd
from datetime import datetime, timedelta
from models.stock import StockQuote, KLineBar, StockSearchResult, FundamentalData, SentimentData

PERIOD_MAP = {"day": "daily", "week": "weekly", "month": "monthly"}

class AShareService:
    def get_quote(self, code: str) -> StockQuote:
        df = ak.stock_zh_a_spot_em()
        row = df[df["代码"] == code]
        if row.empty:
            raise ValueError(f"Stock {code} not found")
        r = row.iloc[0]
        price = float(r["最新价"])
        prev_close = float(r["昨收"])
        return StockQuote(
            code=code,
            name=str(r["名称"]),
            market="A",
            price=price,
            change=round(float(r["涨跌额"]), 4),
            change_pct=round(float(r["涨跌幅"]), 2),
            volume=float(r["成交量"]),
            amount=float(r["成交额"]),
            high=float(r["最高"]),
            low=float(r["最低"]),
            open=float(r["今开"]),
            prev_close=prev_close,
        )

    def get_kline(self, code: str, period: str = "day") -> list[KLineBar]:
        end = datetime.now().strftime("%Y%m%d")
        start = (datetime.now() - timedelta(days=365 * 3)).strftime("%Y%m%d")
        df = ak.stock_zh_a_hist(
            symbol=code,
            period=PERIOD_MAP.get(period, "daily"),
            start_date=start,
            end_date=end,
            adjust="qfq",
        )
        return [
            KLineBar(
                date=str(row["日期"]),
                open=round(float(row["开盘"]), 4),
                high=round(float(row["最高"]), 4),
                low=round(float(row["最低"]), 4),
                close=round(float(row["收盘"]), 4),
                volume=float(row["成交量"]),
            )
            for _, row in df.iterrows()
        ]

    def get_fundamental(self, code: str) -> FundamentalData:
        try:
            df = ak.stock_financial_abstract(stock=code)
            latest = df.iloc[0] if not df.empty else None
        except Exception:
            latest = None
        try:
            spot_df = ak.stock_zh_a_spot_em()
            spot = spot_df[spot_df["代码"] == code].iloc[0]
            pe = float(spot.get("市盈率-动态", 0)) or None
            pb = float(spot.get("市净率", 0)) or None
        except Exception:
            pe, pb = None, None
        score = 50
        if pe and 0 < pe < 20:
            score += 15
        elif pe and pe > 60:
            score -= 15
        return FundamentalData(pe_ttm=pe, pb=pb, score=max(0, min(100, score)))

    def get_sentiment(self, code: str) -> SentimentData:
        try:
            df = ak.stock_individual_fund_flow(stock=code, market="sh" if code.startswith("6") else "sz")
            latest = df.iloc[-1]
            return SentimentData(
                main_net_inflow=float(latest.get("主力净流入-净额", 0)),
                super_large_net=float(latest.get("超大单净流入-净额", 0)),
                large_net=float(latest.get("大单净流入-净额", 0)),
                score=_score_sentiment(float(latest.get("主力净流入-净额", 0))),
            )
        except Exception:
            return SentimentData()

    def search(self, query: str) -> list[StockSearchResult]:
        try:
            df = ak.stock_info_a_code_name()
            mask = df["name"].str.contains(query, na=False) | df["code"].str.contains(query, na=False)
            matched = df[mask].head(10)
            return [
                StockSearchResult(code=row["code"], name=row["name"], market="A")
                for _, row in matched.iterrows()
            ]
        except Exception:
            return []

def _score_sentiment(net_inflow: float) -> int:
    if net_inflow > 100_000_000:
        return 80
    elif net_inflow > 0:
        return 65
    elif net_inflow > -100_000_000:
        return 40
    return 25
```

- [ ] **Step 4: 运行测试，确认通过**

```bash
pytest tests/test_a_share.py -v
```

- [ ] **Step 5: Commit**

```bash
git add backend/services/a_share.py backend/tests/test_a_share.py
git commit -m "feat: A-share data service via akshare"
```

---

### Task 7: 港股数据服务 (akshare)

**Files:**
- Create: `backend/services/hk_stock.py`

- [ ] **Step 1: 编写测试**

`backend/tests/test_hk_stock.py`:
```python
import pytest
import pandas as pd
from unittest.mock import patch
from services.hk_stock import HKStockService
from models.stock import StockQuote, KLineBar

def make_hk_spot_df(code="00700"):
    return pd.DataFrame([{
        "代码": code, "名称": "腾讯控股",
        "最新价": 380.0, "涨跌幅": 2.15, "涨跌额": 8.0,
        "成交量": 15000000, "成交额": 5700000000,
        "最高": 385.0, "最低": 375.0, "今开": 376.0, "昨收": 372.0,
    }])

def make_hk_hist_df():
    return pd.DataFrame({
        "日期": ["2024-01-02", "2024-01-03"],
        "开盘": [376.0, 380.0],
        "最高": [385.0, 388.0],
        "最低": [374.0, 378.0],
        "收盘": [380.0, 385.0],
        "成交量": [15000000, 13000000],
    })

@patch("services.hk_stock.ak.stock_hk_spot_em")
def test_get_quote(mock_spot):
    mock_spot.return_value = make_hk_spot_df("00700")
    svc = HKStockService()
    result = svc.get_quote("00700")
    assert isinstance(result, StockQuote)
    assert result.market == "HK"
    assert result.price == 380.0

@patch("services.hk_stock.ak.stock_hk_hist")
def test_get_kline(mock_hist):
    mock_hist.return_value = make_hk_hist_df()
    svc = HKStockService()
    result = svc.get_kline("00700", period="day")
    assert len(result) == 2
    assert isinstance(result[0], KLineBar)
```

- [ ] **Step 2: 运行测试，确认失败**

```bash
pytest tests/test_hk_stock.py -v
```

- [ ] **Step 3: 实现 services/hk_stock.py**

```python
import akshare as ak
from datetime import datetime, timedelta
from models.stock import StockQuote, KLineBar, StockSearchResult

PERIOD_MAP = {"day": "daily", "week": "weekly", "month": "monthly"}

class HKStockService:
    def get_quote(self, code: str) -> StockQuote:
        df = ak.stock_hk_spot_em()
        row = df[df["代码"] == code]
        if row.empty:
            raise ValueError(f"HK stock {code} not found")
        r = row.iloc[0]
        return StockQuote(
            code=code,
            name=str(r["名称"]),
            market="HK",
            price=float(r["最新价"]),
            change=round(float(r["涨跌额"]), 4),
            change_pct=round(float(r["涨跌幅"]), 2),
            volume=float(r["成交量"]),
            amount=float(r["成交额"]),
            high=float(r["最高"]),
            low=float(r["最低"]),
            open=float(r["今开"]),
            prev_close=float(r["昨收"]),
        )

    def get_kline(self, code: str, period: str = "day") -> list[KLineBar]:
        end = datetime.now().strftime("%Y%m%d")
        start = (datetime.now() - timedelta(days=365 * 3)).strftime("%Y%m%d")
        df = ak.stock_hk_hist(
            symbol=code,
            period=PERIOD_MAP.get(period, "daily"),
            start_date=start,
            end_date=end,
            adjust="qfq",
        )
        return [
            KLineBar(
                date=str(row["日期"]),
                open=round(float(row["开盘"]), 4),
                high=round(float(row["最高"]), 4),
                low=round(float(row["最低"]), 4),
                close=round(float(row["收盘"]), 4),
                volume=float(row["成交量"]),
            )
            for _, row in df.iterrows()
        ]

    def search(self, query: str) -> list[StockSearchResult]:
        try:
            df = ak.stock_hk_spot_em()
            mask = df["名称"].str.contains(query, na=False) | df["代码"].str.contains(query, na=False)
            return [
                StockSearchResult(code=row["代码"], name=row["名称"], market="HK")
                for _, row in df[mask].head(10).iterrows()
            ]
        except Exception:
            return []
```

- [ ] **Step 4: 运行测试，确认通过**

```bash
pytest tests/test_hk_stock.py -v
```

- [ ] **Step 5: Commit**

```bash
git add backend/services/hk_stock.py backend/tests/test_hk_stock.py
git commit -m "feat: HK stock data service via akshare"
```

---

### Task 8: 基本面与估值分析服务

**Files:**
- Create: `backend/services/fundamental.py`

- [ ] **Step 1: 编写测试**

`backend/tests/test_fundamental.py`:
```python
import pytest
from services.fundamental import compute_valuation, score_fundamental
from models.stock import FundamentalData, ValuationData

def test_peg_calculated_correctly():
    fd = FundamentalData(pe_ttm=20.0, revenue_growth_yoy=25.0, score=60)
    result = compute_valuation(fd, industry_avg_pe=25.0, industry_avg_pb=3.0)
    assert isinstance(result, ValuationData)
    assert result.peg is not None
    assert abs(result.peg - (20.0 / 25.0)) < 0.01

def test_score_increases_with_good_metrics():
    fd = FundamentalData(pe_ttm=15.0, pb=1.5, roe=20.0, revenue_growth_yoy=15.0,
                          gross_margin=45.0, debt_ratio=30.0, score=50)
    score = score_fundamental(fd)
    assert score > 60

def test_score_decreases_with_bad_metrics():
    fd = FundamentalData(pe_ttm=100.0, pb=10.0, roe=2.0, revenue_growth_yoy=-10.0,
                          gross_margin=5.0, debt_ratio=80.0, score=50)
    score = score_fundamental(fd)
    assert score < 40

def test_valuation_score_below_industry_avg_is_positive():
    fd = FundamentalData(pe_ttm=15.0, score=50)
    result = compute_valuation(fd, industry_avg_pe=30.0, industry_avg_pb=4.0)
    assert result.score > 50
```

- [ ] **Step 2: 运行测试，确认失败**

```bash
pytest tests/test_fundamental.py -v
```

- [ ] **Step 3: 实现 services/fundamental.py**

```python
from models.stock import FundamentalData, ValuationData

def score_fundamental(fd: FundamentalData) -> int:
    score = 50
    if fd.pe_ttm:
        if 0 < fd.pe_ttm < 15:
            score += 15
        elif 15 <= fd.pe_ttm < 30:
            score += 5
        elif fd.pe_ttm > 60:
            score -= 15
    if fd.roe:
        if fd.roe > 20:
            score += 15
        elif fd.roe > 10:
            score += 5
        elif fd.roe < 5:
            score -= 10
    if fd.revenue_growth_yoy:
        if fd.revenue_growth_yoy > 20:
            score += 10
        elif fd.revenue_growth_yoy < 0:
            score -= 10
    if fd.gross_margin:
        if fd.gross_margin > 40:
            score += 5
        elif fd.gross_margin < 10:
            score -= 5
    if fd.debt_ratio:
        if fd.debt_ratio > 70:
            score -= 10
        elif fd.debt_ratio < 40:
            score += 5
    return max(0, min(100, score))

def compute_valuation(
    fd: FundamentalData,
    industry_avg_pe: float | None,
    industry_avg_pb: float | None,
    pe_percentile: float | None = None,
    pb_percentile: float | None = None,
) -> ValuationData:
    peg = None
    if fd.pe_ttm and fd.revenue_growth_yoy and fd.revenue_growth_yoy > 0:
        peg = round(fd.pe_ttm / fd.revenue_growth_yoy, 2)

    score = 50
    if industry_avg_pe and fd.pe_ttm:
        ratio = fd.pe_ttm / industry_avg_pe
        if ratio < 0.7:
            score += 20
        elif ratio < 1.0:
            score += 10
        elif ratio > 1.5:
            score -= 15

    if peg:
        if peg < 1.0:
            score += 10
        elif peg > 2.0:
            score -= 10

    if pe_percentile is not None:
        if pe_percentile < 30:
            score += 10
        elif pe_percentile > 70:
            score -= 10

    return ValuationData(
        industry_avg_pe=industry_avg_pe,
        industry_avg_pb=industry_avg_pb,
        pe_percentile=pe_percentile,
        pb_percentile=pb_percentile,
        peg=peg,
        score=max(0, min(100, score)),
    )
```

- [ ] **Step 4: 运行测试，确认通过**

```bash
pytest tests/test_fundamental.py -v
```

- [ ] **Step 5: Commit**

```bash
git add backend/services/fundamental.py backend/tests/test_fundamental.py
git commit -m "feat: fundamental scoring and valuation analysis"
```

---

### Task 9: 新闻抓取服务

**Files:**
- Create: `backend/services/news_service.py`

- [ ] **Step 1: 编写测试**

`backend/tests/test_news_service.py`:
```python
import pytest
import pandas as pd
from unittest.mock import patch
from services.news_service import NewsService
from models.news import NewsItem

def make_news_df():
    return pd.DataFrame([
        {"标题": "央行降息预期升温", "发布时间": "2024-01-03 09:00:00",
         "文章链接": "http://example.com/1", "来源": "东方财富"},
        {"标题": "科技股集体上涨", "发布时间": "2024-01-03 10:00:00",
         "文章链接": "http://example.com/2", "来源": "新浪财经"},
    ])

@patch("services.news_service.ak.stock_news_em")
def test_get_stock_news(mock_news):
    mock_news.return_value = make_news_df()
    svc = NewsService()
    result = svc.get_stock_news("600036", market="A")
    assert isinstance(result, list)
    assert len(result) == 2
    assert isinstance(result[0], NewsItem)

@patch("services.news_service.ak.news_economic_baidu")
def test_get_hot_news(mock_hot):
    mock_hot.return_value = make_news_df()
    svc = NewsService()
    result = svc.get_hot_news(market="all")
    assert isinstance(result, list)
    assert len(result) > 0
```

- [ ] **Step 2: 运行测试，确认失败**

```bash
pytest tests/test_news_service.py -v
```

- [ ] **Step 3: 实现 services/news_service.py**

```python
import akshare as ak
import hashlib
from datetime import datetime
from models.news import NewsItem

class NewsService:
    def get_stock_news(self, code: str, market: str, page: int = 1) -> list[NewsItem]:
        try:
            df = ak.stock_news_em(symbol=code)
            items = []
            start = (page - 1) * 20
            for _, row in df.iloc[start:start + 20].iterrows():
                items.append(NewsItem(
                    id=hashlib.md5(str(row.get("文章链接", "")).encode()).hexdigest(),
                    title=str(row.get("标题", "")),
                    source=str(row.get("来源", "东方财富")),
                    published_at=str(row.get("发布时间", "")),
                    url=str(row.get("文章链接", "")),
                    related_stocks=[code],
                ))
            return items
        except Exception:
            return []

    def get_hot_news(self, market: str = "all", page: int = 1) -> list[NewsItem]:
        try:
            date_str = datetime.now().strftime("%Y%m%d")
            df = ak.news_economic_baidu(date=date_str)
            items = []
            start = (page - 1) * 20
            for _, row in df.iloc[start:start + 20].iterrows():
                items.append(NewsItem(
                    id=hashlib.md5(str(row.get("链接", "")).encode()).hexdigest(),
                    title=str(row.get("标题", "")),
                    source=str(row.get("来源", "百度财经")),
                    published_at=str(row.get("发布时间", "")),
                    url=str(row.get("链接", "")),
                ))
            return items
        except Exception:
            return []
```

- [ ] **Step 4: 运行测试，确认通过**

```bash
pytest tests/test_news_service.py -v
```

- [ ] **Step 5: Commit**

```bash
git add backend/services/news_service.py backend/tests/test_news_service.py
git commit -m "feat: news service for hot news and stock-specific news"
```

---

### Task 10: AI 研报生成服务 (Claude API)

**Files:**
- Create: `backend/services/ai_report.py`
- Create: `backend/tests/test_ai_report.py`

- [ ] **Step 1: 编写测试**

`backend/tests/test_ai_report.py`:
```python
import pytest
import json
from unittest.mock import patch, MagicMock
from services.ai_report import AIReportService, build_prompt
from models.stock import (StockAnalysis, TechnicalIndicators, FundamentalData,
                           ValuationData, SentimentData, ChipData, MacroData, AIReport)

def make_analysis():
    return StockAnalysis(
        code="600036", name="招商银行", market="A",
        technical=TechnicalIndicators(ma5=42.0, ma20=40.0, rsi6=55.0, score=65),
        fundamental=FundamentalData(pe_ttm=7.5, pb=1.0, roe=16.0, score=75),
        valuation=ValuationData(industry_avg_pe=10.0, peg=0.8, score=70),
        sentiment=SentimentData(main_net_inflow=500_000_000, score=75),
        chip=ChipData(institution_holding_pct=45.0, score=65),
        macro=MacroData(industry_name="银行", industry_change_pct=1.5, score=60),
    )

def test_build_prompt_contains_stock_name():
    analysis = make_analysis()
    prompt = build_prompt(analysis)
    assert "招商银行" in prompt
    assert "600036" in prompt

def test_build_prompt_contains_all_dimensions():
    analysis = make_analysis()
    prompt = build_prompt(analysis)
    assert "技术面" in prompt
    assert "基本面" in prompt
    assert "估值" in prompt
    assert "情绪" in prompt

@patch("services.ai_report.anthropic.Anthropic")
def test_generate_report_returns_ai_report(mock_client_cls):
    mock_client = MagicMock()
    mock_client_cls.return_value = mock_client
    fake_response_text = json.dumps({
        "summary": "招商银行基本面优秀，当前估值合理，建议买入。",
        "market_review": "近期股价企稳回升。",
        "fundamental_analysis": "ROE持续保持在16%以上。",
        "technical_analysis": "MACD金叉，RSI处于健康区间。",
        "valuation_analysis": "PEG<1，低于行业平均PE。",
        "sentiment_analysis": "主力净流入5亿，资金积极。",
        "risks": ["宏观经济下行风险", "息差收窄压力", "资产质量风险"],
        "recommendation": "建议以当前价位分批买入，目标价45-48元。",
        "rating": "买入",
        "target_price_low": 45.0,
        "target_price_high": 48.0,
    })
    mock_client.messages.create.return_value = MagicMock(
        content=[MagicMock(text=fake_response_text)]
    )
    svc = AIReportService(api_key="test_key")
    analysis = make_analysis()
    result = svc.generate(analysis)
    assert isinstance(result, AIReport)
    assert result.rating == "买入"
    assert len(result.risks) == 3
    assert result.code == "600036"
```

- [ ] **Step 2: 运行测试，确认失败**

```bash
pytest tests/test_ai_report.py -v
```

- [ ] **Step 3: 实现 services/ai_report.py**

```python
import json
import anthropic
from datetime import datetime
from models.stock import StockAnalysis, AIReport

SYSTEM_PROMPT = """你是一位资深券商首席分析师，拥有20年A股、港股、美股研究经验。
请根据提供的股票六维量化数据，用专业的研报风格撰写完整的投资分析报告。
报告必须客观、专业、有据可依，风险提示要真实充分，不得做出无根据的极端预测。
输出必须是严格的 JSON 格式，字段完全按照用户指定的结构输出，不添加任何额外说明。"""

def build_prompt(analysis: StockAnalysis) -> str:
    return f"""请对 {analysis.name}（{analysis.code}，{analysis.market}股）进行完整投资分析。

=== 六维量化数据 ===

【技术面评分: {analysis.technical.score}/100】
- MA5={analysis.technical.ma5}, MA20={analysis.technical.ma20}, MA60={analysis.technical.ma60}
- MACD: DIF={analysis.technical.macd_dif}, DEA={analysis.technical.macd_dea}, 柱={analysis.technical.macd_bar}
- RSI6={analysis.technical.rsi6}, RSI12={analysis.technical.rsi12}
- KDJ: K={analysis.technical.kdj_k}, D={analysis.technical.kdj_d}, J={analysis.technical.kdj_j}
- 布林带: 上={analysis.technical.boll_upper}, 中={analysis.technical.boll_mid}, 下={analysis.technical.boll_lower}
- 量比={analysis.technical.volume_ratio}, 换手率={analysis.technical.turnover_rate}

【基本面评分: {analysis.fundamental.score}/100】
- PE(TTM)={analysis.fundamental.pe_ttm}, PB={analysis.fundamental.pb}, ROE={analysis.fundamental.roe}%
- 营收同比增速={analysis.fundamental.revenue_growth_yoy}%, 净利润同比增速={analysis.fundamental.profit_growth_yoy}%
- 毛利率={analysis.fundamental.gross_margin}%, 净利率={analysis.fundamental.net_margin}%
- 资产负债率={analysis.fundamental.debt_ratio}%, EPS={analysis.fundamental.eps}
- 股息率={analysis.fundamental.dividend_yield}%

【估值面评分: {analysis.valuation.score}/100】
- 行业平均PE={analysis.valuation.industry_avg_pe}, 行业平均PB={analysis.valuation.industry_avg_pb}
- 历史PE分位数={analysis.valuation.pe_percentile}%, PEG={analysis.valuation.peg}

【情绪面评分: {analysis.sentiment.score}/100】
- 主力净流入={analysis.sentiment.main_net_inflow}元
- 超大单净额={analysis.sentiment.super_large_net}元, 大单净额={analysis.sentiment.large_net}元
- 北向资金净买入={analysis.sentiment.northbound_net}元
- 融资余额变化={analysis.sentiment.margin_balance}, 新闻热度={analysis.sentiment.news_heat_score}/100

【筹码与机构评分: {analysis.chip.score}/100】
- 机构持仓占比={analysis.chip.institution_holding_pct}%
- 股东人数变化={analysis.chip.shareholder_count_change_pct}%
- 主要股东变化: {analysis.chip.top10_holder_change}

【宏观与行业评分: {analysis.macro.score}/100】
- 所属行业: {analysis.macro.industry_name}
- 行业近期涨跌: {analysis.macro.industry_change_pct}%
- 与大盘相关系数: {analysis.macro.corr_with_index}
- 政策摘要: {analysis.macro.policy_summary}

=== 输出要求 ===
请严格输出以下 JSON 结构（不要添加 markdown 代码块标记）：
{{
  "summary": "（100字内核心结论）",
  "market_review": "（近期行情复盘，2-3句）",
  "fundamental_analysis": "（基本面深度解读，3-5句）",
  "technical_analysis": "（技术形态分析，3-5句）",
  "valuation_analysis": "（估值合理性评估，2-3句）",
  "sentiment_analysis": "（资金情绪分析，2-3句）",
  "risks": ["风险1", "风险2", "风险3（至少3条）"],
  "recommendation": "（操作建议，包含买卖时机参考，3-5句）",
  "rating": "（从以下选一：强烈买入/买入/持有/卖出/强烈卖出）",
  "target_price_low": 数字或null,
  "target_price_high": 数字或null
}}"""

class AIReportService:
    def __init__(self, api_key: str):
        self.client = anthropic.Anthropic(api_key=api_key)

    def generate(self, analysis: StockAnalysis) -> AIReport:
        response = self.client.messages.create(
            model="claude-sonnet-4-6",
            max_tokens=2048,
            system=SYSTEM_PROMPT,
            messages=[{"role": "user", "content": build_prompt(analysis)}],
        )
        raw = response.content[0].text.strip()
        data = json.loads(raw)
        return AIReport(
            code=analysis.code,
            name=analysis.name,
            market=analysis.market,
            generated_at=datetime.now().isoformat(),
            **data,
        )
```

- [ ] **Step 4: 运行测试，确认通过**

```bash
pytest tests/test_ai_report.py -v
```

- [ ] **Step 5: Commit**

```bash
git add backend/services/ai_report.py backend/tests/test_ai_report.py
git commit -m "feat: AI report generator via Claude API with structured JSON output"
```

---

### Task 11: 股票路由（所有端点）

**Files:**
- Modify: `backend/routers/stock.py`
- Create: `backend/tests/test_stock_router.py`

- [ ] **Step 1: 编写路由集成测试**

`backend/tests/test_stock_router.py`:
```python
import pytest
from fastapi.testclient import TestClient
from unittest.mock import patch, MagicMock, AsyncMock
from main import app
from models.stock import StockSearchResult, StockQuote, KLineBar

client = TestClient(app)

def make_quote(code="600036", market="A"):
    return StockQuote(code=code, name="招商银行", market=market, price=42.5,
                      change=0.5, change_pct=1.2, volume=5e7, amount=2.1e9,
                      high=43.0, low=42.0, open=42.1, prev_close=42.0)

@patch("routers.stock.get_cache", new_callable=AsyncMock, return_value=None)
@patch("routers.stock.set_cache", new_callable=AsyncMock)
@patch("routers.stock.AShareService")
def test_get_quote_a_share(mock_svc_cls, mock_set, mock_get):
    mock_svc_cls.return_value.get_quote.return_value = make_quote()
    resp = client.get("/stock/600036/quote?market=A")
    assert resp.status_code == 200
    data = resp.json()
    assert data["code"] == "600036"
    assert data["price"] == 42.5

@patch("routers.stock.get_cache", new_callable=AsyncMock, return_value=None)
@patch("routers.stock.set_cache", new_callable=AsyncMock)
@patch("routers.stock.USStockService")
def test_get_quote_us_stock(mock_svc_cls, mock_set, mock_get):
    mock_svc_cls.return_value.get_quote.return_value = make_quote("AAPL", "US")
    resp = client.get("/stock/AAPL/quote?market=US")
    assert resp.status_code == 200
    assert resp.json()["market"] == "US"

def test_search_missing_query_returns_422():
    resp = client.get("/stock/search")
    assert resp.status_code == 422

@patch("routers.stock.AShareService")
def test_search_returns_list(mock_svc_cls):
    mock_svc_cls.return_value.search.return_value = [
        StockSearchResult(code="600036", name="招商银行", market="A")
    ]
    resp = client.get("/stock/search?q=招商&market=A")
    assert resp.status_code == 200
    assert isinstance(resp.json(), list)
```

- [ ] **Step 2: 运行测试，确认失败**

```bash
pytest tests/test_stock_router.py -v
```

- [ ] **Step 3: 实现 routers/stock.py**

```python
from fastapi import APIRouter, HTTPException, Request, Query
from config import settings
from services.cache import CacheService
from services.a_share import AShareService
from services.hk_stock import HKStockService
from services.us_stock import USStockService
from services.indicators import calculate_indicators
from services.fundamental import score_fundamental, compute_valuation
from services.ai_report import AIReportService
from models.stock import StockAnalysis, TechnicalIndicators, FundamentalData, ValuationData, SentimentData, ChipData, MacroData
import pandas as pd

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
        quote = svc.get_quote(code)
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
    bars = svc.get_kline(code, period)
    data = [b.model_dump() for b in bars]
    await set_cache(request, cache_key, data, settings.cache_ttl_quote)
    return data

@router.get("/{code}/analysis")
async def get_analysis(code: str, market: str, request: Request):
    cache_key = f"analysis:{market}:{code}"
    cached = await get_cache(request, cache_key)
    if cached:
        return cached
    svc = _get_service(market)
    try:
        quote = svc.get_quote(code)
        bars = svc.get_kline(code, period="day")
        df = pd.DataFrame([b.model_dump() for b in bars])
        technical = calculate_indicators(df)
        fundamental = svc.get_fundamental(code) if hasattr(svc, "get_fundamental") else FundamentalData()
        fundamental.score = score_fundamental(fundamental)
        valuation = compute_valuation(fundamental, industry_avg_pe=None, industry_avg_pb=None)
        sentiment = svc.get_sentiment(code) if hasattr(svc, "get_sentiment") else SentimentData()
        analysis = StockAnalysis(
            code=code, name=quote.name, market=market,
            technical=technical, fundamental=fundamental,
            valuation=valuation, sentiment=sentiment,
            chip=ChipData(), macro=MacroData(),
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    data = analysis.model_dump()
    await set_cache(request, cache_key, data, settings.cache_ttl_quote)
    return data

@router.get("/{code}/report")
async def get_report(code: str, market: str, request: Request):
    cache_key = f"report:{market}:{code}"
    cached = await get_cache(request, cache_key)
    if cached:
        cached["cached"] = True
        return cached
    analysis_resp = await get_analysis(code, market, request)
    analysis = StockAnalysis(**analysis_resp)
    svc = AIReportService(api_key=settings.claude_api_key)
    try:
        report = svc.generate(analysis)
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"AI report generation failed: {e}")
    data = report.model_dump()
    await set_cache(request, cache_key, data, settings.cache_ttl_report)
    return data
```

- [ ] **Step 4: 运行测试，确认通过**

```bash
pytest tests/test_stock_router.py -v
```

- [ ] **Step 5: Commit**

```bash
git add backend/routers/stock.py backend/tests/test_stock_router.py
git commit -m "feat: stock router with search/quote/kline/analysis/report endpoints"
```

---

### Task 12: 新闻路由

**Files:**
- Modify: `backend/routers/news.py`
- Create: `backend/tests/test_news_router.py`

- [ ] **Step 1: 编写测试**

`backend/tests/test_news_router.py`:
```python
from fastapi.testclient import TestClient
from unittest.mock import patch
from main import app
from models.news import NewsItem

client = TestClient(app)

def make_news():
    return [NewsItem(id="abc", title="测试新闻", source="东方财富",
                     published_at="2024-01-03 10:00:00", url="http://example.com")]

@patch("routers.news.NewsService")
def test_get_hot_news(mock_svc_cls):
    mock_svc_cls.return_value.get_hot_news.return_value = make_news()
    resp = client.get("/news/hot")
    assert resp.status_code == 200
    assert isinstance(resp.json(), list)
    assert resp.json()[0]["title"] == "测试新闻"

@patch("routers.news.NewsService")
def test_get_stock_news(mock_svc_cls):
    mock_svc_cls.return_value.get_stock_news.return_value = make_news()
    resp = client.get("/news/stock/600036?market=A")
    assert resp.status_code == 200
    assert len(resp.json()) == 1
```

- [ ] **Step 2: 运行测试，确认失败**

```bash
pytest tests/test_news_router.py -v
```

- [ ] **Step 3: 实现 routers/news.py**

```python
from fastapi import APIRouter, Request, Query
from services.news_service import NewsService
from services.cache import CacheService
from config import settings

router = APIRouter()

@router.get("/hot")
async def get_hot_news(request: Request, market: str = "all", page: int = 1):
    cache_key = f"news:hot:{market}:{page}"
    cache = CacheService(request.app.state.redis)
    cached = await cache.get(cache_key)
    if cached:
        return cached
    svc = NewsService()
    items = svc.get_hot_news(market=market, page=page)
    data = [i.model_dump() for i in items]
    await cache.set(cache_key, data, settings.cache_ttl_news)
    return data

@router.get("/stock/{code}")
async def get_stock_news(code: str, request: Request, market: str = Query("A"), page: int = 1):
    cache_key = f"news:stock:{market}:{code}:{page}"
    cache = CacheService(request.app.state.redis)
    cached = await cache.get(cache_key)
    if cached:
        return cached
    svc = NewsService()
    items = svc.get_stock_news(code=code, market=market, page=page)
    data = [i.model_dump() for i in items]
    await cache.set(cache_key, data, settings.cache_ttl_news)
    return data
```

- [ ] **Step 4: 运行测试，确认通过**

```bash
pytest tests/test_news_router.py -v
```

- [ ] **Step 5: 运行全部测试**

```bash
pytest tests/ -v
```

预期：所有测试通过

- [ ] **Step 6: Commit**

```bash
git add backend/routers/news.py backend/tests/test_news_router.py
git commit -m "feat: news router with hot news and stock news endpoints"
```

---

### Task 13: 后端全链路验证

- [ ] **Step 1: 启动 Redis**

```bash
docker run -d -p 6379:6379 --name stock-redis redis
```

- [ ] **Step 2: 启动后端**

```bash
cd backend
venv\Scripts\activate
uvicorn main:app --reload --host 0.0.0.0 --port 8000
```

- [ ] **Step 3: 在 Swagger UI 测试各端点**

访问 `http://localhost:8000/docs`，按顺序测试：
1. `GET /health` → `{"status": "ok"}`
2. `GET /stock/search?q=腾讯&market=A` → 返回股票列表
3. `GET /stock/600036/quote?market=A` → 返回招商银行实时行情
4. `GET /stock/600036/kline?market=A&period=day` → 返回 K 线数据
5. `GET /stock/600036/analysis?market=A` → 返回六维分析数据
6. `GET /stock/600036/report?market=A` → 返回 AI 研报（需要 `.env` 中有效 API Key）
7. `GET /news/hot` → 返回热点新闻
8. `GET /news/stock/600036?market=A` → 返回个股新闻

- [ ] **Step 4: 验证缓存生效**

第二次请求 `/stock/600036/report?market=A` 应在 1 秒内返回（第一次约 5-10 秒）。

- [ ] **Step 5: Commit 最终**

```bash
git add .
git commit -m "feat: backend complete - all endpoints verified"
```
