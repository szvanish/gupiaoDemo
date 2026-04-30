import yfinance as yf
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
            price=round(float(price), 4),
            change=round(float(change), 4),
            change_pct=round(float(change_pct), 2),
            volume=float(info.get("volume", 0)),
            amount=float(info.get("volume", 0)) * float(price),
            high=float(info.get("dayHigh", price)),
            low=float(info.get("dayLow", price)),
            open=float(info.get("open", price)),
            prev_close=round(float(prev_close), 4),
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
                open=round(float(row["Open"]), 4),
                high=round(float(row["High"]), 4),
                low=round(float(row["Low"]), 4),
                close=round(float(row["Close"]), 4),
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
            if hasattr(yf, 'search'):
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
            # Fallback: return empty list if search not available
            return []
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
