package com.stockanalyzer.ui.detail;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014J\u0016\u0010\u0013\u001a\u00020\u00102\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0002J\u0010\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\b\u0010 \u001a\u00020\u0010H\u0002J\b\u0010!\u001a\u00020\u0010H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\""}, d2 = {"Lcom/stockanalyzer/ui/detail/StockDetailActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/stockanalyzer/databinding/ActivityStockDetailBinding;", "code", "", "market", "name", "viewModel", "Lcom/stockanalyzer/ui/detail/StockDetailViewModel;", "getViewModel", "()Lcom/stockanalyzer/ui/detail/StockDetailViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "renderKline", "bars", "", "Lcom/stockanalyzer/data/model/KLineBar;", "renderQuote", "q", "Lcom/stockanalyzer/data/model/StockQuote;", "renderRadar", "analysis", "Lcom/stockanalyzer/data/model/StockAnalysis;", "renderReport", "r", "Lcom/stockanalyzer/data/model/AIReport;", "setupObservers", "setupPeriodTabs", "app_debug"})
public final class StockDetailActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.stockanalyzer.databinding.ActivityStockDetailBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private java.lang.String code;
    private java.lang.String name;
    private java.lang.String market;
    
    public StockDetailActivity() {
        super();
    }
    
    private final com.stockanalyzer.ui.detail.StockDetailViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupPeriodTabs() {
    }
    
    private final void setupObservers() {
    }
    
    private final void renderQuote(com.stockanalyzer.data.model.StockQuote q) {
    }
    
    private final void renderKline(java.util.List<com.stockanalyzer.data.model.KLineBar> bars) {
    }
    
    private final void renderRadar(com.stockanalyzer.data.model.StockAnalysis analysis) {
    }
    
    private final void renderReport(com.stockanalyzer.data.model.AIReport r) {
    }
}