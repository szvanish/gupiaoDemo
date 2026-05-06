package com.stockanalyzer.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stockanalyzer.data.model.NewsItem
import com.stockanalyzer.data.repository.NewsRepository
import com.stockanalyzer.data.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val stockRepository: StockRepository
) : ViewModel() {

    private val _news = MutableLiveData<List<NewsItem>>()
    val news: LiveData<List<NewsItem>> = _news

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    var currentTab = 0

    fun loadHotNews(market: String = "all") {
        if (currentTab == 1) {
            loadWatchlistNews()
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            newsRepository.getHotNews(market).onSuccess { _news.value = it }
            _isLoading.value = false
        }
    }

    private fun loadWatchlistNews() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val watchlist = stockRepository.getWatchlistFlow().first()
                if (watchlist.isEmpty()) {
                    _news.value = emptyList()
                } else {
                    val allNews = mutableListOf<NewsItem>()
                    for (stock in watchlist.take(5)) {
                        newsRepository.getStockNews(stock.code, stock.market)
                            .onSuccess { allNews.addAll(it.take(5)) }
                    }
                    _news.value = allNews
                }
            } catch (e: Exception) {
                _news.value = emptyList()
            }
            _isLoading.value = false
        }
    }
}
