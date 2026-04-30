import os
# Bypass proxy: clear env vars, set NO_PROXY, and monkey-patch requests so Windows registry
# proxy settings cannot be picked up by new Sessions created inside akshare.
for _k in ("HTTP_PROXY", "HTTPS_PROXY", "http_proxy", "https_proxy", "ALL_PROXY", "all_proxy"):
    os.environ.pop(_k, None)
os.environ["NO_PROXY"] = "*"
os.environ["no_proxy"] = "*"

import requests.utils as _ru
_ru.get_environ_proxies = lambda *a, **kw: {}  # prevent Windows registry proxy lookup

from fastapi import FastAPI
from contextlib import asynccontextmanager
import redis.asyncio as aioredis
from routers import stock, news
from config import settings

@asynccontextmanager
async def lifespan(app: FastAPI):
    app.state.redis = aioredis.from_url(settings.redis_url, decode_responses=True)
    yield
    await app.state.redis.aclose()

app = FastAPI(title="Stock Analyzer API", version="1.0.0", lifespan=lifespan)
app.include_router(stock.router, prefix="/stock", tags=["stock"])
app.include_router(news.router, prefix="/news", tags=["news"])

@app.get("/health")
async def health():
    return {"status": "ok"}
