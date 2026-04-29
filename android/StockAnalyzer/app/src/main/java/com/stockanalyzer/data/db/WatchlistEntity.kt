package com.stockanalyzer.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey val id: String,
    val code: String,
    val name: String,
    val market: String,
    val sortOrder: Int = 0,
    val addedAt: Long = System.currentTimeMillis()
)
