package com.stockanalyzer.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stockanalyzer.data.model.NewsItem
import com.stockanalyzer.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _news = MutableLiveData<List<NewsItem>>()
    val news: LiveData<List<NewsItem>> = _news

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    var currentTab = 0

    fun loadHotNews(market: String = "all") {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getHotNews(market)
                .onSuccess { _news.value = it }
            _isLoading.value = false
        }
    }
}
