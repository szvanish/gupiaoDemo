# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

A full-stack stock analysis platform with a Python/FastAPI backend and an Android (Kotlin) mobile client. Supports China A-shares, Hong Kong, and US markets with real-time data, 6-dimensional analysis, and AI-generated investment reports via DeepSeek.

## Backend (Python/FastAPI)

### Commands

```bash
# Install dependencies
pip install -r backend/requirements.txt

# Run development server
cd backend
uvicorn main:app --reload --host 0.0.0.0 --port 8000

# Run all tests
cd backend && pytest tests/ -v

# Run a single test file
cd backend && pytest tests/test_stock_router.py -v
```

### Architecture

```
backend/
├── main.py          # FastAPI app, router registration, CORS config
├── config.py        # Settings via pydantic-settings (Redis URL, DeepSeek key, cache TTLs)
├── routers/         # stock.py and news.py — thin HTTP layer, delegates to services
├── services/        # Business logic:
│   ├── a_share.py       # China A-shares (akshare + Sina Finance HQ API)
│   ├── hk_stock.py      # Hong Kong stocks
│   ├── us_stock.py      # US stocks (yfinance)
│   ├── indicators.py    # Technical indicators: MA, MACD, RSI, KDJ, Bollinger Bands
│   ├── fundamental.py   # Valuation scoring (PE/PB/PEG, percentile ranking)
│   ├── ai_report.py     # DeepSeek API → Chinese-language investment report
│   ├── news.py          # CCTV hot news + stock-specific news
│   └── cache.py         # Redis async client with keyed TTLs
└── models/          # Pydantic v2 request/response schemas
```

**Request flow:** `Router → Service → External API/Cache → Pydantic Model → JSON response`

**Cache TTLs (Redis):**
- Quotes: 300s, Financials: 86400s, News: 300s, AI Reports: 7200s
- Cache keys pattern: `{type}:{market}:{code}` (e.g., `quote:A:000001`)

**Key API endpoints:**
- `GET /stock/search?q=&market=A|HK|US`
- `GET /stock/{code}/quote?market=`
- `GET /stock/{code}/kline?market=&period=day|week|month`
- `GET /stock/{code}/analysis?market=` — 6D analysis (scores 0–100 per dimension)
- `GET /stock/{code}/report?market=` — AI-generated report
- `GET /news/hot?market=all&page=1`

**6 Analysis Dimensions:** Technical, Fundamental, Valuation, Sentiment, Chip Distribution, Macro — each 0–100 scored.

## Android App (Kotlin)

### Commands (run from `android/StockAnalyzer/`)

```bash
./gradlew assembleDebug      # Build debug APK
./gradlew assembleRelease    # Build release APK
./gradlew test               # Unit tests
./gradlew installDebug       # Install to connected device
```

### Architecture

MVVM + Repository + Hilt DI. minSdk 26, compileSdk/targetSdk 35.

```
app/src/main/java/com/stockanalyzer/
├── ui/
│   ├── search/       # SearchFragment + SearchViewModel (debounced search)
│   ├── watchlist/    # WatchlistFragment + ViewModel (Room-persisted)
│   ├── news/         # NewsFragment + ViewModel
│   ├── detail/       # StockDetailActivity + ViewModel (MPAndroidChart)
│   └── settings/     # SettingsFragment
├── data/
│   ├── repository/   # Repository implementations (API + Room)
│   ├── api/          # Retrofit2 service interfaces
│   └── db/           # Room DAOs and entities
├── di/               # Hilt modules (NetworkModule, DatabaseModule)
└── model/            # Data classes matching backend API responses
```

**Backend URL:** Configured in the Retrofit module — uses the device's LAN IP (real device requires actual network IP, not `localhost`). Check `NetworkModule` for the base URL constant.

## Environment Setup

Backend requires a `.env` file (see `backend/.env.example`):
- `DEEPSEEK_API_KEY` — DeepSeek API key for AI report generation
- `REDIS_URL` — Redis connection string (default: `redis://localhost:6379`)

## External Data Sources

| Source | Used For |
|--------|----------|
| akshare | A-share fundamentals, sentiment flows, chip data |
| Sina Finance HQ API | Real-time A-share quotes |
| yfinance | US stock prices and fundamentals |
| DeepSeek API | Chinese-language AI investment report generation |
| CCTV News API | Hot financial news |
