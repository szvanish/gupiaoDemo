package com.stockanalyzer.data.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0003\u0010\u0005\u001a\u00020\u00062\b\b\u0003\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ2\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u000b\u001a\u00020\u00062\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0003\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\f\u00a8\u0006\r"}, d2 = {"Lcom/stockanalyzer/data/api/NewsApiService;", "", "getHotNews", "", "Lcom/stockanalyzer/data/model/NewsItem;", "market", "", "page", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getStockNews", "code", "(Ljava/lang/String;Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface NewsApiService {
    
    @retrofit2.http.GET(value = "news/hot")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getHotNews(@retrofit2.http.Query(value = "market")
    @org.jetbrains.annotations.NotNull()
    java.lang.String market, @retrofit2.http.Query(value = "page")
    int page, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stockanalyzer.data.model.NewsItem>> $completion);
    
    @retrofit2.http.GET(value = "news/stock/{code}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getStockNews(@retrofit2.http.Path(value = "code")
    @org.jetbrains.annotations.NotNull()
    java.lang.String code, @retrofit2.http.Query(value = "market")
    @org.jetbrains.annotations.NotNull()
    java.lang.String market, @retrofit2.http.Query(value = "page")
    int page, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.stockanalyzer.data.model.NewsItem>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}