import akshare as ak
import requests
import pandas as pd
from datetime import datetime, timedelta
from models.stock import StockQuote, KLineBar, StockSearchResult, FundamentalData, SentimentData

_SINA_HEADERS = {"Referer": "https://finance.sina.com.cn"}

def _sym(code: str) -> str:
    if code.startswith(('8', '4')):
        return "bj" + code
    return ("sh" if code.startswith("6") else "sz") + code

_name_cache: dict[str, str] = {}

def _sina_realtime(code: str) -> dict | None:
    """Fetch real-time quote from Sina HQ API. Returns None on any failure."""
    try:
        r = requests.get(
            f"https://hq.sinajs.cn/list={_sym(code)}", timeout=5,
            headers=_SINA_HEADERS
        )
        inner = r.text.split('"')[1] if '"' in r.text else ""
        if not inner or inner.startswith(","):
            return None
        f = inner.split(",")
        if len(f) < 10 or not f[3]:
            return None
        def _f(i):
            try: return float(f[i]) if f[i] else None
            except (ValueError, IndexError): return None
        return {
            "name":       f[0],
            "open":       _f(1),
            "prev_close": _f(2),
            "price":      _f(3),
            "high":       _f(4),
            "low":        _f(5),
            "volume":     _f(8),
            "amount":     _f(9),
        }
    except Exception:
        return None

def _get_stock_name(code: str) -> str:
    if code in _name_cache:
        return _name_cache[code]
    rt = _sina_realtime(code)
    if rt and rt.get("name"):
        _name_cache[code] = rt["name"]
        return rt["name"]
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
    """Fetch daily OHLCV from East Money via ak.stock_zh_a_hist (domestic, fast)."""
    end = datetime.now().strftime("%Y%m%d")
    start = (datetime.now() - timedelta(days=days)).strftime("%Y%m%d")
    df = ak.stock_zh_a_hist(symbol=code, period="daily", start_date=start, end_date=end, adjust="")
    if df.empty:
        return df
    df = df.rename(columns={
        "日期": "date", "开盘": "open", "最高": "high",
        "最低": "low", "收盘": "close", "成交量": "volume", "成交额": "amount",
    })
    return df[["date", "open", "high", "low", "close", "volume", "amount"]]


class AShareService:
    def get_quote(self, code: str) -> StockQuote:
        rt = _sina_realtime(code)
        if rt and rt.get("price") and rt["price"] > 0:
            prev_close = rt["prev_close"] or rt["price"]
            price = rt["price"]
            change = round(price - prev_close, 4)
            change_pct = round((change / prev_close * 100) if prev_close else 0, 2)
            name = rt.get("name") or _get_stock_name(code)
            if name:
                _name_cache[code] = name
            return StockQuote(
                code=code, name=name or code, market="A",
                price=price, change=change, change_pct=change_pct,
                volume=rt.get("volume") or 0,
                amount=rt.get("amount") or 0,
                high=rt.get("high") or price,
                low=rt.get("low") or price,
                open=rt.get("open") or price,
                prev_close=prev_close,
            )
        # Fall back to stooq daily
        df = _fetch_daily(code, 15)
        if df.empty:
            raise ValueError(f"Stock {code} not found")
        last = df.iloc[-1]
        prev_close = float(df.iloc[-2]["close"]) if len(df) >= 2 else float(last["close"])
        price = float(last["close"])
        change = round(price - prev_close, 4)
        change_pct = round((change / prev_close * 100) if prev_close else 0, 2)
        return StockQuote(
            code=code, name=_get_stock_name(code), market="A",
            price=price, change=change, change_pct=change_pct,
            volume=float(last["volume"]), amount=float(last["amount"]),
            high=float(last["high"]), low=float(last["low"]),
            open=float(last["open"]), prev_close=prev_close,
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
        eps = roe = roa = net_margin = gross_margin = None
        profit_growth = revenue_growth = debt_ratio = current_ratio = None
        book_value_per_share = None

        try:
            year = str(datetime.now().year - 2)
            df = ak.stock_financial_analysis_indicator(symbol=code, start_year=year)
            if not df.empty:
                # Use most recent report row
                row = df.iloc[-1]
                def _fval(idx):
                    try:
                        v = float(row.iloc[idx])
                        return None if pd.isna(v) else v
                    except (ValueError, TypeError):
                        return None

                eps               = _fval(1)   # 摊薄每股收益(元)
                book_value_per_share = _fval(5) # 每股净资产_调整前(元)
                roa               = _fval(11)  # 总资产利润率(%)
                net_margin        = _fval(17)  # 销售净利率(%)
                roe               = _fval(28)  # 净资产收益率(%)
                gross_margin      = _fval(21)  # 销售毛利率(%)
                revenue_growth    = _fval(31)  # 主营业务收入增长率(%)
                profit_growth     = _fval(32)  # 净利润增长率(%)
                current_ratio     = _fval(45)  # 流动比率
                debt_ratio        = _fval(61)  # 资产负债率(%)

                # For TTM EPS: use prior-year data to compute trailing 12-month EPS
                if len(df) >= 2:
                    try:
                        ttm = _compute_ttm_eps(df)
                        if ttm is not None:
                            eps = ttm
                    except Exception:
                        pass
        except Exception:
            pass

        # Compute PE and PB from real-time price + financial data
        pe, pb = None, None
        try:
            rt = _sina_realtime(code)
            price = rt["price"] if rt else None
            if price and price > 0:
                if eps and eps > 0:
                    pe = round(price / eps, 2)
                if book_value_per_share and book_value_per_share > 0:
                    pb = round(price / book_value_per_share, 2)
        except Exception:
            pass

        score = 50
        if roe:
            if roe > 15: score += 15
            elif roe < 5: score -= 10
        if pe:
            if 0 < pe < 20: score += 10
            elif pe > 60: score -= 15
        if profit_growth:
            if profit_growth > 20: score += 10
            elif profit_growth < -10: score -= 10

        return FundamentalData(
            pe_ttm=pe, pb=pb, eps=eps,
            roe=roe, roa=roa,
            net_margin=net_margin, gross_margin=gross_margin,
            revenue_growth_yoy=revenue_growth,
            profit_growth_yoy=profit_growth,
            debt_ratio=debt_ratio,
            current_ratio=current_ratio,
            score=max(0, min(100, score)),
        )

    def get_sentiment(self, code: str) -> SentimentData:
        try:
            market = "sh" if code.startswith("6") else "sz"
            df = ak.stock_individual_fund_flow(stock=code, market=market)
            latest = df.iloc[-1]
            main_net    = float(latest.get("主力净流入-净额", 0) or 0)
            super_large = float(latest.get("超大单净流入-净额", 0) or 0)
            large       = float(latest.get("大单净流入-净额", 0) or 0)
            return SentimentData(
                main_net_inflow=main_net,
                super_large_net=super_large,
                large_net=large,
                score=_score_sentiment(main_net),
            )
        except Exception:
            return SentimentData()

    def search(self, query: str) -> list[StockSearchResult]:
        frames = []
        try:
            df = ak.stock_info_sh_name_code(symbol="主板A股")[["证券代码", "证券简称"]]
            df.columns = ["code", "name"]
            frames.append(df)
        except Exception:
            pass
        try:
            df = ak.stock_info_sz_name_code(symbol="A股列表")[["A股代码", "A股简称"]]
            df["A股代码"] = df["A股代码"].astype(str).str.zfill(6)
            df.columns = ["code", "name"]
            frames.append(df)
        except Exception:
            pass
        try:
            df = ak.stock_info_sh_name_code(symbol="科创板")[["证券代码", "证券简称"]]
            df.columns = ["code", "name"]
            frames.append(df)
        except Exception:
            pass
        try:
            df = ak.stock_info_bj_name_code()[["证券代码", "证券简称"]]
            df.columns = ["code", "name"]
            frames.append(df)
        except Exception:
            pass
        if not frames:
            return []
        big_df = pd.concat(frames, ignore_index=True)
        # Strip spaces from names before matching (some exchanges store "五 粮 液" with spaces)
        name_normalized = big_df["name"].str.replace(" ", "", regex=False)
        mask = name_normalized.str.contains(query, na=False) | big_df["code"].str.contains(query, na=False)
        return [
            StockSearchResult(code=row["code"], name=row["name"].replace(" ", ""), market="A")
            for _, row in big_df[mask].head(10).iterrows()
        ]


def _compute_ttm_eps(df: pd.DataFrame) -> float | None:
    """
    Compute TTM EPS from cumulative YTD quarterly reports.
    Sina's col 1 (摊薄每股收益) is cumulative within each fiscal year.
    TTM = latest_ytd + prev_full_year - prev_same_period_ytd
    """
    try:
        dates = pd.to_datetime(df.iloc[:, 0])
        eps_vals = df.iloc[:, 1].astype(float)
        latest_date = dates.iloc[-1]
        latest_eps = eps_vals.iloc[-1]
        if pd.isna(latest_eps):
            return None

        # If it's a full-year report (Dec 31), just return it
        if latest_date.month == 12:
            return latest_eps

        # Find the previous full-year report
        prev_annual_mask = (dates.dt.month == 12) & (dates.dt.year == latest_date.year - 1)
        prev_same_mask = (dates.dt.month == latest_date.month) & (dates.dt.year == latest_date.year - 1)

        prev_annual = eps_vals[prev_annual_mask].iloc[-1] if prev_annual_mask.any() else None
        prev_same   = eps_vals[prev_same_mask].iloc[-1] if prev_same_mask.any() else None

        if prev_annual is not None and prev_same is not None and not pd.isna(prev_annual) and not pd.isna(prev_same):
            ttm = float(latest_eps) + float(prev_annual) - float(prev_same)
            return round(ttm, 4) if ttm > 0 else None
    except Exception:
        pass
    return None


def _score_sentiment(net_inflow: float) -> int:
    if net_inflow > 100_000_000:
        return 80
    elif net_inflow > 0:
        return 65
    elif net_inflow > -100_000_000:
        return 40
    return 25
