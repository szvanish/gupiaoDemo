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
                StockSearchResult(code=str(row["代码"]), name=str(row["名称"]), market="HK")
                for _, row in df[mask].head(10).iterrows()
            ]
        except Exception:
            return []
