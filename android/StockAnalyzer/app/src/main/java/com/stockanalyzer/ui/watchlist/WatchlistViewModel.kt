package com.stockanalyzer.ui.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.stockanalyzer.data.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    val watchlist = repository.getWatchlistFlow().asLiveData()

    fun removeStock(id: String) {
        viewModelScope.launch { repository.removeFromWatchlist(id) }
    }
}
