package com.stockanalyzer.data.repository

import com.stockanalyzer.data.api.NewsApiService
import com.stockanalyzer.data.model.NewsItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val api: NewsApiService) {

    suspend fun getHotNews(market: String = "all", page: Int = 1): Result<List<NewsItem>> =
        runCatching { api.getHotNews(market, page) }

    suspend fun getStockNews(code: String, market: String, page: Int = 1): Result<List<NewsItem>> =
        runCatching { api.getStockNews(code, market, page) }
}
