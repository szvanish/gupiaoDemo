import json
from typing import Any, Optional

class CacheService:
    def __init__(self, redis):
        self.redis = redis

    async def get(self, key: str) -> Optional[Any]:
        value = await self.redis.get(key)
        if value is None:
            return None
        return json.loads(value)

    async def set(self, key: str, data: Any, ttl: int) -> None:
        await self.redis.setex(key, ttl, json.dumps(data))

    async def delete(self, key: str) -> None:
        await self.redis.delete(key)
