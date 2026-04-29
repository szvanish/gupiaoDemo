package com.stockanalyzer.ui.detail;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J \u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020\t2\b\b\u0002\u0010\'\u001a\u00020\tJ\u0016\u0010(\u001a\u00020$2\u0006\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020\tJ\u001e\u0010)\u001a\u00020$2\u0006\u0010%\u001a\u00020\t2\u0006\u0010*\u001a\u00020\t2\u0006\u0010&\u001a\u00020\tR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0010\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0010\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0019\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0018R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0018R\u001d\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0018R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00120\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0018R\u0019\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0018R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006+"}, d2 = {"Lcom/stockanalyzer/ui/detail/StockDetailViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/stockanalyzer/data/repository/StockRepository;", "(Lcom/stockanalyzer/data/repository/StockRepository;)V", "_analysis", "Landroidx/lifecycle/MutableLiveData;", "Lcom/stockanalyzer/data/model/StockAnalysis;", "_error", "", "_isInWatchlist", "", "kotlin.jvm.PlatformType", "_isLoadingReport", "_kline", "", "Lcom/stockanalyzer/data/model/KLineBar;", "_quote", "Lcom/stockanalyzer/data/model/StockQuote;", "_report", "Lcom/stockanalyzer/data/model/AIReport;", "analysis", "Landroidx/lifecycle/LiveData;", "getAnalysis", "()Landroidx/lifecycle/LiveData;", "error", "getError", "isInWatchlist", "isLoadingReport", "kline", "getKline", "quote", "getQuote", "report", "getReport", "load", "", "code", "market", "period", "loadReport", "toggleWatchlist", "name", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class StockDetailViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.stockanalyzer.data.repository.StockRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.stockanalyzer.data.model.StockQuote> _quote = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.stockanalyzer.data.model.StockQuote> quote = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.stockanalyzer.data.model.KLineBar>> _kline = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.stockanalyzer.data.model.KLineBar>> kline = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.stockanalyzer.data.model.StockAnalysis> _analysis = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.stockanalyzer.data.model.StockAnalysis> analysis = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.stockanalyzer.data.model.AIReport> _report = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.stockanalyzer.data.model.AIReport> report = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _isInWatchlist = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Boolean> isInWatchlist = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _isLoadingReport = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Boolean> isLoadingReport = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _error = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.String> error = null;
    
    @javax.inject.Inject()
    public StockDetailViewModel(@org.jetbrains.annotations.NotNull()
    com.stockanalyzer.data.repository.StockRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.stockanalyzer.data.model.StockQuote> getQuote() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.stockanalyzer.data.model.KLineBar>> getKline() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.stockanalyzer.data.model.StockAnalysis> getAnalysis() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.stockanalyzer.data.model.AIReport> getReport() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Boolean> isInWatchlist() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Boolean> isLoadingReport() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.String> getError() {
        return null;
    }
    
    public final void load(@org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    java.lang.String market, @org.jetbrains.annotations.NotNull()
    java.lang.String period) {
    }
    
    public final void loadReport(@org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    java.lang.String market) {
    }
    
    public final void toggleWatchlist(@org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String market) {
    }
}