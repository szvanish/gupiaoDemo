import pytest
import pandas as pd
from unittest.mock import patch
from services.hk_stock import HKStockService
from models.stock import StockQuote, KLineBar

def make_hk_spot_df(code="00700"):
    return pd.DataFrame([{
        "代码": code, "名称": "腾讯控股",
        "最新价": 380.0, "涨跌幅": 2.15, "涨跌额": 8.0,
        "成交量": 15000000.0, "成交额": 5700000000.0,
        "最高": 385.0, "最低": 375.0, "今开": 376.0, "昨收": 372.0,
    }])

def make_hk_hist_df():
    return pd.DataFrame({
        "日期": ["2024-01-02", "2024-01-03"],
        "开盘": [376.0, 380.0],
        "最高": [385.0, 388.0],
        "最低": [374.0, 378.0],
        "收盘": [380.0, 385.0],
        "成交量": [15000000.0, 13000000.0],
    })

@patch("services.hk_stock.ak.stock_hk_spot_em")
def test_get_quote(mock_spot):
    mock_spot.return_value = make_hk_spot_df("00700")
    svc = HKStockService()
    result = svc.get_quote("00700")
    assert isinstance(result, StockQuote)
    assert result.market == "HK"
    assert result.price == 380.0

@patch("services.hk_stock.ak.stock_hk_hist")
def test_get_kline(mock_hist):
    mock_hist.return_value = make_hk_hist_df()
    svc = HKStockService()
    result = svc.get_kline("00700", period="day")
    assert len(result) == 2
    assert isinstance(result[0], KLineBar)
