package com.stockanalyzer.data.repository

import com.stockanalyzer.data.api.StockApiService
import com.stockanalyzer.data.db.WatchlistDao
import com.stockanalyzer.data.db.WatchlistEntity
import com.stockanalyzer.data.model.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepository @Inject constructor(
    private val api: StockApiService,
    private val dao: WatchlistDao
) {
    suspend fun search(query: String, market: String): Result<List<StockSearchResult>> =
        runCatching { api.searchStock(query, market) }

    suspend fun getQuote(code: String, market: String): Result<StockQuote> =
        runCatching { api.getQuote(code, market) }

    suspend fun getKline(code: String, market: String, period: String): Result<List<KLineBar>> =
        runCatching { api.getKline(code, market, period) }

    suspend fun getAnalysis(code: String, market: String): Result<StockAnalysis> =
        runCatching { api.getAnalysis(code, market) }

    suspend fun getReport(code: String, market: String): Result<AIReport> =
        runCatching { api.getReport(code, market) }

    fun getWatchlistFlow(): Flow<List<WatchlistEntity>> = dao.getAllFlow()

    suspend fun addToWatchlist(code: String, name: String, market: String) {
        dao.insert(WatchlistEntity(id = "$market:$code", code = code, name = name, market = market))
    }

    suspend fun removeFromWatchlist(id: String) {
        dao.findById(id)?.let { dao.delete(it) }
    }

    suspend fun isInWatchlist(code: String, market: String): Boolean =
        dao.findById("$market:$code") != null
}
