package com.stockanalyzer.data.api

import com.stockanalyzer.data.model.NewsItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {

    @GET("news/hot")
    suspend fun getHotNews(
        @Query("market") market: String = "all",
        @Query("page") page: Int = 1
    ): List<NewsItem>

    @GET("news/stock/{code}")
    suspend fun getStockNews(
        @Path("code") code: String,
        @Query("market") market: String,
        @Query("page") page: Int = 1
    ): List<NewsItem>
}
