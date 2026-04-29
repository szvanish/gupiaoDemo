import pytest
import pandas as pd
from unittest.mock import patch
from services.news_service import NewsService
from models.news import NewsItem

def make_news_df():
    return pd.DataFrame([
        {"标题": "央行降息预期升温", "发布时间": "2024-01-03 09:00:00",
         "文章链接": "http://example.com/1", "来源": "东方财富"},
        {"标题": "科技股集体上涨", "发布时间": "2024-01-03 10:00:00",
         "文章链接": "http://example.com/2", "来源": "新浪财经"},
    ])

@patch("services.news_service.ak.stock_news_em")
def test_get_stock_news(mock_news):
    mock_news.return_value = make_news_df()
    svc = NewsService()
    result = svc.get_stock_news("600036", market="A")
    assert isinstance(result, list)
    assert len(result) == 2
    assert isinstance(result[0], NewsItem)

def test_get_hot_news_returns_list():
    svc = NewsService()
    # Just test that it returns a list without crashing
    # We don't mock since different akshare versions have different hot news functions
    result = svc.get_hot_news(market="all")
    assert isinstance(result, list)
