import pytest
import json
from unittest.mock import AsyncMock
from services.cache import CacheService

@pytest.fixture
def mock_redis():
    redis = AsyncMock()
    redis.get = AsyncMock(return_value=None)
    redis.setex = AsyncMock()
    return redis

@pytest.mark.asyncio
async def test_get_returns_none_on_miss(mock_redis):
    cache = CacheService(mock_redis)
    result = await cache.get("missing_key")
    assert result is None

@pytest.mark.asyncio
async def test_set_and_get(mock_redis):
    data = {"price": 100.5, "name": "腾讯"}
    mock_redis.get = AsyncMock(return_value=json.dumps(data))
    cache = CacheService(mock_redis)
    await cache.set("test_key", data, ttl=300)
    result = await cache.get("test_key")
    assert result == data

@pytest.mark.asyncio
async def test_set_calls_setex_with_correct_ttl(mock_redis):
    cache = CacheService(mock_redis)
    await cache.set("key", {"val": 1}, ttl=600)
    mock_redis.setex.assert_called_once_with("key", 600, json.dumps({"val": 1}))
