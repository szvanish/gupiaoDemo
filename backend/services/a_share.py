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
            spot_df = ak.stock_zh_a_spot_em()
            spot = spot_df[spot_df["代码"] == code]
            if not spot.empty:
                r = spot.iloc[0]
                pe = r.get("市盈率-动态") if hasattr(r, 'get') else None
                if pe is None:
                    pe = r["市盈率-动态"] if "市盈率-动态" in spot.columns else None
                pb = r["市净率"] if "市净率" in spot.columns else None
                pe = float(pe) if pe is not None else None
                pb = float(pb) if pb is not None else None
            else:
                pe, pb = None, None
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
