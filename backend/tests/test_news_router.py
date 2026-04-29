from fastapi.testclient import TestClient
from unittest.mock import patch, AsyncMock
import sys, os
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
from main import app
from models.news import NewsItem

client = TestClient(app)

def make_news():
    return [NewsItem(id="abc", title="测试新闻", source="东方财富",
                     published_at="2024-01-03 10:00:00", url="http://example.com")]

@patch("routers.news.get_cache", new_callable=AsyncMock, return_value=None)
@patch("routers.news.set_cache", new_callable=AsyncMock)
@patch("routers.news.NewsService")
def test_get_hot_news(mock_svc_cls, mock_set, mock_get):
    mock_svc_cls.return_value.get_hot_news.return_value = make_news()
    resp = client.get("/news/hot")
    assert resp.status_code == 200
    assert isinstance(resp.json(), list)
    assert resp.json()[0]["title"] == "测试新闻"

@patch("routers.news.get_cache", new_callable=AsyncMock, return_value=None)
@patch("routers.news.set_cache", new_callable=AsyncMock)
@patch("routers.news.NewsService")
def test_get_stock_news(mock_svc_cls, mock_set, mock_get):
    mock_svc_cls.return_value.get_stock_news.return_value = make_news()
    resp = client.get("/news/stock/600036?market=A")
    assert resp.status_code == 200
    assert len(resp.json()) == 1
