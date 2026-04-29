package com.stockanalyzer.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stockanalyzer.data.model.StockSearchResult
import com.stockanalyzer.data.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    private val _results = MutableLiveData<List<StockSearchResult>>()
    val results: LiveData<List<StockSearchResult>> = _results

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    var currentMarket = "A"
    private var searchJob: Job? = null

    fun search(query: String) {
        searchJob?.cancel()
        if (query.isBlank()) {
            _results.value = emptyList()
            return
        }
        searchJob = viewModelScope.launch {
            delay(300)
            _isLoading.value = true
            repository.search(query, currentMarket)
                .onSuccess { _results.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }
}
