package com.stockanalyzer.data.model

import com.google.gson.annotations.SerializedName

data class NewsItem(
    val id: String,
    val title: String,
    val source: String,
    @SerializedName("published_at") val publishedAt: String,
    val url: String,
    @SerializedName("related_stocks") val relatedStocks: List<String> = emptyList(),
    val summary: String?
)
