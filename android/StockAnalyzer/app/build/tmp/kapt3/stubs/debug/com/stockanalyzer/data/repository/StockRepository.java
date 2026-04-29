package com.stockanalyzer.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J&\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\rJ,\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J:\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00140\u000f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0017\u0010\rJ,\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u000f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001a\u0010\u0012J,\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001d\u0010\u0012J\u0012\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u00140\u001fJ\u001e\u0010!\u001a\u00020\"2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010#\u001a\u00020\b2\u0006\u0010$\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010%J2\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\'0\u00140\u000f2\u0006\u0010(\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b)\u0010\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006*"}, d2 = {"Lcom/stockanalyzer/data/repository/StockRepository;", "", "api", "Lcom/stockanalyzer/data/api/StockApiService;", "dao", "Lcom/stockanalyzer/data/db/WatchlistDao;", "(Lcom/stockanalyzer/data/api/StockApiService;Lcom/stockanalyzer/data/db/WatchlistDao;)V", "addToWatchlist", "", "code", "", "name", "market", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAnalysis", "Lkotlin/Result;", "Lcom/stockanalyzer/data/model/StockAnalysis;", "getAnalysis-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getKline", "", "Lcom/stockanalyzer/data/model/KLineBar;", "period", "getKline-BWLJW6A", "getQuote", "Lcom/stockanalyzer/data/model/StockQuote;", "getQuote-0E7RQCE", "getReport", "Lcom/stockanalyzer/data/model/AIReport;", "getReport-0E7RQCE", "getWatchlistFlow", "Lkotlinx/coroutines/flow/Flow;", "Lcom/stockanalyzer/data/db/WatchlistEntity;", "isInWatchlist", "", "removeFromWatchlist", "id", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "search", "Lcom/stockanalyzer/data/model/StockSearchResult;", "query", "search-0E7RQCE", "app_debug"})
public final class StockRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.stockanalyzer.data.api.StockApiService api = null;
    @org.jetbrains.annotations.NotNull()
    private final com.stockanalyzer.data.db.WatchlistDao dao = null;
    
    @javax.inject.Inject()
    public StockRepository(@org.jetbrains.annotations.NotNull()
    com.stockanalyzer.data.api.StockApiService api, @org.jetbrains.annotations.NotNull()
    com.stockanalyzer.data.db.WatchlistDao dao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.stockanalyzer.data.db.WatchlistEntity>> getWatchlistFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addToWatchlist(@org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String market, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object removeFromWatchlist(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object isInWatchlist(@org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    java.lang.String market, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
}