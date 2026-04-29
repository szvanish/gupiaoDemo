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
        "volume": (abs(close) * 100000 + 1000000).astype(float),
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
