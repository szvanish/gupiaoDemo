import pandas as pd
import ta
from models.stock import TechnicalIndicators

def calculate_indicators(df: pd.DataFrame) -> TechnicalIndicators:
    close = df["close"]
    high = df["high"]
    low = df["low"]
    volume = df["volume"]

    ma5 = close.rolling(5).mean().iloc[-1]
    ma10 = close.rolling(10).mean().iloc[-1]
    ma20 = close.rolling(20).mean().iloc[-1]
    ma60 = close.rolling(60).mean().iloc[-1] if len(df) >= 60 else None

    macd_ind = ta.trend.MACD(close)
    macd_dif = macd_ind.macd().iloc[-1]
    macd_dea = macd_ind.macd_signal().iloc[-1]
    macd_bar = macd_ind.macd_diff().iloc[-1]

    rsi6 = ta.momentum.RSIIndicator(close, window=6).rsi().iloc[-1]
    rsi12 = ta.momentum.RSIIndicator(close, window=12).rsi().iloc[-1]
    rsi24 = ta.momentum.RSIIndicator(close, window=24).rsi().iloc[-1] if len(df) >= 24 else None

    stoch = ta.momentum.StochasticOscillator(high, low, close)
    kdj_k = stoch.stoch().iloc[-1]
    kdj_d = stoch.stoch_signal().iloc[-1]
    kdj_j = 3 * kdj_k - 2 * kdj_d if not (pd.isna(kdj_k) or pd.isna(kdj_d)) else None

    bb = ta.volatility.BollingerBands(close)
    boll_upper = bb.bollinger_hband().iloc[-1]
    boll_mid = bb.bollinger_mavg().iloc[-1]
    boll_lower = bb.bollinger_lband().iloc[-1]

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
        f = float(val)
        if math.isnan(f):
            return None
        return round(f, 4)
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
