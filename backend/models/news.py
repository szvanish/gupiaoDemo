from pydantic import BaseModel
from typing import Optional

class NewsItem(BaseModel):
    id: str
    title: str
    source: str
    published_at: str
    url: str
    related_stocks: list[str] = []
    summary: Optional[str] = None
