from pydantic_settings import BaseSettings

class Settings(BaseSettings):
    redis_url: str = "redis://localhost:6379"
    deepseek_api_key: str = ""
    cache_ttl_quote: int = 300
    cache_ttl_financials: int = 86400
    cache_ttl_news: int = 300
    cache_ttl_report: int = 7200

    class Config:
        env_file = ".env"

settings = Settings()
