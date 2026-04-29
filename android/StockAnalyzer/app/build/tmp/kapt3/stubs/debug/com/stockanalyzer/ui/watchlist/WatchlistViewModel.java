package com.stockanalyzer.ui.watchlist;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000f"}, d2 = {"Lcom/stockanalyzer/ui/watchlist/WatchlistViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/stockanalyzer/data/repository/StockRepository;", "(Lcom/stockanalyzer/data/repository/StockRepository;)V", "watchlist", "Landroidx/lifecycle/LiveData;", "", "Lcom/stockanalyzer/data/db/WatchlistEntity;", "getWatchlist", "()Landroidx/lifecycle/LiveData;", "removeStock", "", "id", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class WatchlistViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.stockanalyzer.data.repository.StockRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.stockanalyzer.data.db.WatchlistEntity>> watchlist = null;
    
    @javax.inject.Inject()
    public WatchlistViewModel(@org.jetbrains.annotations.NotNull()
    com.stockanalyzer.data.repository.StockRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.stockanalyzer.data.db.WatchlistEntity>> getWatchlist() {
        return null;
    }
    
    public final void removeStock(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
}