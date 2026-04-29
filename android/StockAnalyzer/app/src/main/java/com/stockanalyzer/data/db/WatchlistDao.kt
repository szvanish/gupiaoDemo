package com.stockanalyzer.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {

    @Query("SELECT * FROM watchlist ORDER BY sortOrder ASC, addedAt DESC")
    fun getAllFlow(): Flow<List<WatchlistEntity>>

    @Query("SELECT * FROM watchlist WHERE id = :id LIMIT 1")
    suspend fun findById(id: String): WatchlistEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: WatchlistEntity)

    @Delete
    suspend fun delete(entity: WatchlistEntity)

    @Query("UPDATE watchlist SET sortOrder = :order WHERE id = :id")
    suspend fun updateOrder(id: String, order: Int)
}
