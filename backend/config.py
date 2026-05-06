from pydantic_settings import BaseSettings

class Settings(BaseSettings):
    redis_url: str = "redis://localhost:6379"
    deepseek_api_key: str = ""
    cache_ttl_quote: int = 30        # quote: near-realtime, 30s
    cache_ttl_kline: int = 300       # kline/analysis: 5min
    cache_ttl_financials: int = 86400
    cache_ttl_news: int = 300
    cache_ttl_report: int = 7200

    class Config:
        env_file = ".env"

settings = Settings()
