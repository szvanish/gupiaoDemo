package com.stockanalyzer.data.api

import com.stockanalyzer.data.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StockApiService {

    @GET("stock/search")
    suspend fun searchStock(
        @Query("q") query: String,
        @Query("market") market: String
    ): List<StockSearchResult>

    @GET("stock/{code}/quote")
    suspend fun getQuote(
        @Path("code") code: String,
        @Query("market") market: String
    ): StockQuote

    @GET("stock/{code}/kline")
    suspend fun getKline(
        @Path("code") code: String,
        @Query("market") market: String,
        @Query("period") period: String = "day"
    ): List<KLineBar>

    @GET("stock/{code}/analysis")
    suspend fun getAnalysis(
        @Path("code") code: String,
        @Query("market") market: String
    ): StockAnalysis

    @GET("stock/{code}/report")
    suspend fun getReport(
        @Path("code") code: String,
        @Query("market") market: String
    ): AIReport
}
