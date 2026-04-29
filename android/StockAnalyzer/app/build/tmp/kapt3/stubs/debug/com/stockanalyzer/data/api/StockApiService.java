package com.stockanalyzer.data.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0007J2\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u00052\b\b\u0003\u0010\u000b\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\fJ\"\u0010\r\u001a\u00020\u000e2\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\"\u0010\u000f\u001a\u00020\u00102\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0007J(\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\t2\b\b\u0001\u0010\u0013\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0007\u00a8\u0006\u0014"}, d2 = {"Lcom/stockanalyzer/data/api/StockApiService;", "", "getAnalysis", "Lcom/stockanalyzer/data/model/StockAnalysis;", "code", "", "market", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getKline", "", "Lcom/stockanalyzer/data/model/KLineBar;", "period", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getQuote", "Lcom/stockanalyzer/data/model/StockQuote;", "getReport", "Lcom/stockanalyzer/data/model/AIReport;", "searchStock", "Lcom/stockanalyzer/data/model/StockSearchResult;", "query", "app_debug"})
public abstract interface StockApiService {
    
    @retrofit2.http.GET(value = "stock/search")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchStock(@retrofit2.http.Query(value = "q")
    @org.jetbrains.annotations.NotNull()
    java.lang.String query, @retrofit2.http.Query(value = "market")
    @org.jetbrains.annotations.NotNull()
    java.lang.String market, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stockanalyzer.data.model.StockSearchResult>> $completion);
    
    @retrofit2.http.GET(value = "stock/{code}/quote")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQuote(@retrofit2.http.Path(value = "code")
    @org.jetbrains.annotations.NotNull()
    java.lang.String code, @retrofit2.http.Query(value = "market")
    @org.jetbrains.annotations.NotNull()
    java.lang.String market, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stockanalyzer.data.model.StockQuote> $completion);
    
    @retrofit2.http.GET(value = "stock/{code}/kline")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getKline(@retrofit2.http.Path(value = "code")
    @org.jetbrains.annotations.NotNull()
    java.lang.String code, @retrofit2.http.Query(value = "market")
    @org.jetbrains.annotations.NotNull()
    java.lang.String market, @retrofit2.http.Query(value = "period")
    @org.jetbrains.annotations.NotNull()
    java.lang.String period, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stockanalyzer.data.model.KLineBar>> $completion);
    
    @retrofit2.http.GET(value = "stock/{code}/analysis")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAnalysis(@retrofit2.http.Path(value = "code")
    @org.jetbrains.annotations.NotNull()
    java.lang.String code, @retrofit2.http.Query(value = "market")
    @org.jetbrains.annotations.NotNull()
    java.lang.String market, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stockanalyzer.data.model.StockAnalysis> $completion);
    
    @retrofit2.http.GET(value = "stock/{code}/report")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getReport(@retrofit2.http.Path(value = "code")
    @org.jetbrains.annotations.NotNull()
    java.lang.String code, @retrofit2.http.Query(value = "market")
    @org.jetbrains.annotations.NotNull()
    java.lang.String market, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.stockanalyzer.data.model.AIReport> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}