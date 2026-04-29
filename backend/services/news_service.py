import akshare as ak
import hashlib
from datetime import datetime
from models.news import NewsItem

class NewsService:
    def get_stock_news(self, code: str, market: str, page: int = 1) -> list[NewsItem]:
        try:
            df = ak.stock_news_em(symbol=code)
            items = []
            start = (page - 1) * 20
            for _, row in df.iloc[start:start + 20].iterrows():
                items.append(NewsItem(
                    id=hashlib.md5(str(row.get("文章链接", "")).encode()).hexdigest(),
                    title=str(row.get("标题", "")),
                    source=str(row.get("来源", "东方财富")),
                    published_at=str(row.get("发布时间", "")),
                    url=str(row.get("文章链接", "")),
                    related_stocks=[code],
                ))
            return items
        except Exception:
            return []

    def get_hot_news(self, market: str = "all", page: int = 1) -> list[NewsItem]:
        try:
            # Try different akshare news functions based on version
            df = None
            if hasattr(ak, 'news_economic_baidu'):
                date_str = datetime.now().strftime("%Y%m%d")
                df = ak.news_economic_baidu(date=date_str)
            elif hasattr(ak, 'stock_news_em'):
                df = ak.stock_news_em(symbol="")

            if df is None or df.empty:
                return []

            items = []
            start = (page - 1) * 20
            for _, row in df.iloc[start:start + 20].iterrows():
                url = str(row.get("链接", row.get("文章链接", "")))
                items.append(NewsItem(
                    id=hashlib.md5(url.encode()).hexdigest(),
                    title=str(row.get("标题", "")),
                    source=str(row.get("来源", "财经新闻")),
                    published_at=str(row.get("发布时间", "")),
                    url=url,
                ))
            return items
        except Exception:
            return []
