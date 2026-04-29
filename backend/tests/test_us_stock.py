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
    np.random.seed(42)
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

@patch("services.us_stock.yf.Ticker")
def test_search_returns_list(mock_ticker_cls):
    mock_ticker_cls.return_value = make_mock_ticker()
    svc = USStockService()
    results = svc.search("Apple")
    assert isinstance(results, list)
