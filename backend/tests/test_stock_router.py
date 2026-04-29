import pytest
from fastapi.testclient import TestClient
from unittest.mock import patch, MagicMock, AsyncMock
import sys
import os
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
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
