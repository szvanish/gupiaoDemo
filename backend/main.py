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
