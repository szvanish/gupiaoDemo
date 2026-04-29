package com.stockanalyzer.ui.detail

import androidx.lifecycle.*
import com.stockanalyzer.data.model.*
import com.stockanalyzer.data.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockDetailViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    private val _quote = MutableLiveData<StockQuote>()
    val quote: LiveData<StockQuote> = _quote

    private val _kline = MutableLiveData<List<KLineBar>>()
    val kline: LiveData<List<KLineBar>> = _kline

    private val _analysis = MutableLiveData<StockAnalysis>()
    val analysis: LiveData<StockAnalysis> = _analysis

    private val _report = MutableLiveData<AIReport?>()
    val report: LiveData<AIReport?> = _report

    private val _isInWatchlist = MutableLiveData(false)
    val isInWatchlist: LiveData<Boolean> = _isInWatchlist

    private val _isLoadingReport = MutableLiveData(false)
    val isLoadingReport: LiveData<Boolean> = _isLoadingReport

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun load(code: String, market: String, period: String = "day") {
        viewModelScope.launch {
            repository.getQuote(code, market).onSuccess { _quote.value = it }
                .onFailure { _error.value = it.message }
            repository.getKline(code, market, period).onSuccess { _kline.value = it }
            repository.getAnalysis(code, market).onSuccess { _analysis.value = it }
            _isInWatchlist.value = repository.isInWatchlist(code, market)
        }
    }

    fun loadReport(code: String, market: String) {
        viewModelScope.launch {
            _isLoadingReport.value = true
            repository.getReport(code, market)
                .onSuccess { _report.value = it }
                .onFailure { _error.value = "研报生成失败: ${it.message}" }
            _isLoadingReport.value = false
        }
    }

    fun toggleWatchlist(code: String, name: String, market: String) {
        viewModelScope.launch {
            val inList = repository.isInWatchlist(code, market)
            if (inList) repository.removeFromWatchlist("$market:$code")
            else repository.addToWatchlist(code, name, market)
            _isInWatchlist.value = !inList
        }
    }
}
