import akshare as ak
import hashlib
from datetime import datetime, timedelta
from models.news import NewsItem

class NewsService:
    def get_stock_news(self, code: str, market: str, page: int = 1) -> list[NewsItem]:
        try:
            df = ak.stock_news_em(symbol=code)
            items = []
            start = (page - 1) * 20
            for _, row in df.iloc[start:start + 20].iterrows():
                url = str(row.get("新闻链接", ""))
                items.append(NewsItem(
                    id=hashlib.md5(url.encode()).hexdigest(),
                    title=str(row.get("新闻标题", "")),
                    source=str(row.get("文章来源", "东方财富")),
                    published_at=str(row.get("发布时间", "")),
                    url=url,
                    related_stocks=[code],
                ))
            return items
        except Exception:
            return []

    def get_hot_news(self, market: str = "all", page: int = 1) -> list[NewsItem]:
        # Try today and yesterday since CCTV news may not publish until end of day
        for delta in (0, 1):
            date_str = (datetime.now() - timedelta(days=delta)).strftime("%Y%m%d")
            try:
                df = ak.news_cctv(date=date_str)
                if df is not None and not df.empty:
                    start = (page - 1) * 20
                    items = []
                    for _, row in df.iloc[start:start + 20].iterrows():
                        title = str(row.get("title", ""))
                        content = str(row.get("content", ""))
                        items.append(NewsItem(
                            id=hashlib.md5((date_str + title).encode()).hexdigest(),
                            title=title,
                            source="央视财经",
                            published_at=str(row.get("date", date_str)),
                            url="",
                            summary=content[:200] if content else None,
                        ))
                    return items
            except Exception:
                continue
        return []
