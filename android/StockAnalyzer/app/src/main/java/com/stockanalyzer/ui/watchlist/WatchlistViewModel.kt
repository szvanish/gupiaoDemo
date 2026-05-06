package com.stockanalyzer.ui.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.stockanalyzer.data.db.WatchlistEntity
import com.stockanalyzer.data.model.StockQuote
import com.stockanalyzer.data.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    val watchlist = repository.getWatchlistFlow().asLiveData()

    private val _quotes = MutableLiveData<Map<String, StockQuote>>(emptyMap())
    val quotes: LiveData<Map<String, StockQuote>> = _quotes

    fun refreshQuotes(list: List<WatchlistEntity>) {
        if (list.isEmpty()) return
        viewModelScope.launch {
            val results = list.map { entity ->
                async {
                    repository.getQuote(entity.code, entity.market)
                        .getOrNull()
                        ?.let { "${entity.market}:${entity.code}" to it }
                }
            }.awaitAll().filterNotNull().toMap()
            _quotes.postValue(results)
        }
    }

    fun removeStock(id: String) {
        viewModelScope.launch { repository.removeFromWatchlist(id) }
    }
}
