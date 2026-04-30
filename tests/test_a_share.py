import pytest
import pandas as pd
from unittest.mock import patch
from services.a_share import AShareService
from models.stock import StockQuote, KLineBar, StockSearchResult

def make_spot_df(code="600036"):
    return pd.DataFrame([{
        "代码": code, "名称": "招商银行",
        "最新价": 42.5, "涨跌幅": 1.23, "涨跌额": 0.51,
        "成交量": 50000000.0, "成交额": 2125000000.0,
        "最高": 43.0, "最低": 42.0, "今开": 42.1,
        "昨收": 41.99,
    }])

def make_hist_df():
    return pd.DataFrame({
        "日期": ["2024-01-02", "2024-01-03"],
        "开盘": [42.0, 42.1],
        "最高": [43.0, 43.2],
        "最低": [41.8, 41.9],
        "收盘": [42.5, 42.8],
        "成交量": [50000000.0, 48000000.0],
    })

@patch("services.a_share.ak.stock_zh_a_spot_em")
def test_get_quote(mock_spot):
    mock_spot.return_value = make_spot_df("600036")
    svc = AShareService()
    result = svc.get_quote("600036")
    assert isinstance(result, StockQuote)
    assert result.market == "A"
    assert result.price == 42.5
    assert result.name == "招商银行"

@patch("services.a_share.ak.stock_zh_a_hist")
def test_get_kline(mock_hist):
    mock_hist.return_value = make_hist_df()
    svc = AShareService()
    result = svc.get_kline("600036", period="day")
    assert isinstance(result, list)
    assert len(result) == 2
    assert isinstance(result[0], KLineBar)

@patch("services.a_share.ak.stock_info_a_code_name")
def test_search(mock_name):
    mock_name.return_value = pd.DataFrame({"code": ["600036", "601398"], "name": ["招商银行", "工商银行"]})
    svc = AShareService()
    results = svc.search("招商")
    assert len(results) >= 1
    assert results[0].code == "600036"
