import akshare as ak
import pandas as pd
from datetime import datetime, timedelta
from models.stock import StockQuote, KLineBar, StockSearchResult, FundamentalData, SentimentData

def _sym(code: str) -> str:
    """Convert bare code like '600036' to stooq/tx symbol like 'sh600036'."""
    return ("sh" if code.startswith("6") else "sz") + code

_name_cache: dict[str, str] = {}

def _get_stock_name(code: str) -> str:
    if code in _name_cache:
        return _name_cache[code]
    try:
        df = ak.stock_info_a_code_name()
        row = df[df["code"] == code]
        if not row.empty:
            name = str(row.iloc[0]["name"])
            _name_cache[code] = name
            return name
    except Exception:
        pass
    return code

def _fetch_daily(code: str, days: int) -> pd.DataFrame:
    """Fetch daily OHLCV from stooq via ak.stock_zh_a_daily (avoids eastmoney)."""
    end = datetime.now().strftime("%Y%m%d")
    start = (datetime.now() - timedelta(days=days)).strftime("%Y%m%d")
    return ak.stock_zh_a_daily(symbol=_sym(code), start_date=start, end_date=end, adjust="")

class AShareService:
    def get_quote(self, code: str) -> StockQuote:
        df = _fetch_daily(code, 15)
        if df.empty:
            raise ValueError(f"Stock {code} not found")
        last = df.iloc[-1]
        prev_close = float(df.iloc[-2]["close"]) if len(df) >= 2 else float(last["close"])
        price = float(last["close"])
        change = round(price - prev_close, 4)
        change_pct = round((change / prev_close * 100) if prev_close else 0, 2)
        name = _get_stock_name(code)
        return StockQuote(
            code=code,
            name=name,
            market="A",
            price=price,
            change=change,
            change_pct=change_pct,
            volume=float(last["volume"]),
            amount=float(last["amount"]),
            high=float(last["high"]),
            low=float(last["low"]),
            open=float(last["open"]),
            prev_close=prev_close,
        )

    def get_kline(self, code: str, period: str = "day") -> list[KLineBar]:
        fetch_days = {"day": 180, "week": 365 * 2, "month": 365 * 5}
        df = _fetch_daily(code, fetch_days.get(period, 180))
        if df.empty:
            return []
        df["date"] = pd.to_datetime(df["date"])
        df = df.set_index("date")
        if period == "week":
            df = df.resample("W-FRI").agg(
                open=("open", "first"), high=("high", "max"),
                low=("low", "min"), close=("close", "last"), volume=("volume", "sum")
            ).dropna()
        elif period == "month":
            df = df.resample("ME").agg(
                open=("open", "first"), high=("high", "max"),
                low=("low", "min"), close=("close", "last"), volume=("volume", "sum")
            ).dropna()
        df = df.reset_index()
        return [
            KLineBar(
                date=str(row["date"])[:10],
                open=round(float(row["open"]), 4),
                high=round(float(row["high"]), 4),
                low=round(float(row["low"]), 4),
                close=round(float(row["close"]), 4),
                volume=float(row["volume"]),
            )
            for _, row in df.iterrows()
        ]

    def get_fundamental(self, code: str) -> FundamentalData:
        pe, pb = None, None
        try:
            info = ak.stock_individual_info_em(symbol=code)
            def _val(label):
                row = info[info.iloc[:, 0] == label]
                if not row.empty:
                    try:
                        return float(str(row.iloc[0, 1]).replace(",", "").replace("--", ""))
                    except (ValueError, TypeError):
                        pass
                return None
            pe = _val("市盈率(动)")
            pb = _val("市净率")
        except Exception:
            pass
        score = 50
        if pe and 0 < pe < 20:
            score += 15
        elif pe and pe > 60:
            score -= 15
        return FundamentalData(pe_ttm=pe, pb=pb, score=max(0, min(100, score)))

    def get_sentiment(self, code: str) -> SentimentData:
        try:
            market = "sh" if code.startswith("6") else "sz"
            df = ak.stock_individual_fund_flow(stock=code, market=market)
            latest = df.iloc[-1]
            main_net = float(latest.get("主力净流入-净额", 0) or 0)
            super_large = float(latest.get("超大单净流入-净额", 0) or 0)
            large = float(latest.get("大单净流入-净额", 0) or 0)
            return SentimentData(
                main_net_inflow=main_net,
                super_large_net=super_large,
                large_net=large,
                score=_score_sentiment(main_net),
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
