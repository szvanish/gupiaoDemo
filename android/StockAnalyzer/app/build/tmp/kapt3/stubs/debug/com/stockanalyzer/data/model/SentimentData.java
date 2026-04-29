package com.stockanalyzer.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u001d\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BW\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\r\u001a\u00020\t\u00a2\u0006\u0002\u0010\u000eJ\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0011J\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0011J\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0011J\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0011J\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0011J\t\u0010\u001f\u001a\u00020\tH\u00c6\u0003J\t\u0010 \u001a\u00020\u000bH\u00c6\u0003J\t\u0010!\u001a\u00020\u000bH\u00c6\u0003J\t\u0010\"\u001a\u00020\tH\u00c6\u0003Jr\u0010#\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\tH\u00c6\u0001\u00a2\u0006\u0002\u0010$J\u0013\u0010%\u001a\u00020\u000b2\b\u0010&\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\'\u001a\u00020\tH\u00d6\u0001J\t\u0010(\u001a\u00020)H\u00d6\u0001R\u0016\u0010\f\u001a\u00020\u000b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000fR\u0016\u0010\n\u001a\u00020\u000b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000fR\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0013\u0010\u0011R\u001a\u0010\u0007\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0014\u0010\u0011R\u0016\u0010\b\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0017\u0010\u0011R\u0011\u0010\r\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0019\u0010\u0011\u00a8\u0006*"}, d2 = {"Lcom/stockanalyzer/data/model/SentimentData;", "", "mainNetInflow", "", "superLargeNet", "largeNet", "northboundNet", "marginBalance", "newsHeatScore", "", "isLimitUp", "", "isLimitDown", "score", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;IZZI)V", "()Z", "getLargeNet", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getMainNetInflow", "getMarginBalance", "getNewsHeatScore", "()I", "getNorthboundNet", "getScore", "getSuperLargeNet", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;IZZI)Lcom/stockanalyzer/data/model/SentimentData;", "equals", "other", "hashCode", "toString", "", "app_debug"})
public final class SentimentData {
    @com.google.gson.annotations.SerializedName(value = "main_net_inflow")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double mainNetInflow = null;
    @com.google.gson.annotations.SerializedName(value = "super_large_net")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double superLargeNet = null;
    @com.google.gson.annotations.SerializedName(value = "large_net")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double largeNet = null;
    @com.google.gson.annotations.SerializedName(value = "northbound_net")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double northboundNet = null;
    @com.google.gson.annotations.SerializedName(value = "margin_balance")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double marginBalance = null;
    @com.google.gson.annotations.SerializedName(value = "news_heat_score")
    private final int newsHeatScore = 0;
    @com.google.gson.annotations.SerializedName(value = "is_limit_up")
    private final boolean isLimitUp = false;
    @com.google.gson.annotations.SerializedName(value = "is_limit_down")
    private final boolean isLimitDown = false;
    private final int score = 0;
    
    public SentimentData(@org.jetbrains.annotations.Nullable()
    java.lang.Double mainNetInflow, @org.jetbrains.annotations.Nullable()
    java.lang.Double superLargeNet, @org.jetbrains.annotations.Nullable()
    java.lang.Double largeNet, @org.jetbrains.annotations.Nullable()
    java.lang.Double northboundNet, @org.jetbrains.annotations.Nullable()
    java.lang.Double marginBalance, int newsHeatScore, boolean isLimitUp, boolean isLimitDown, int score) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMainNetInflow() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getSuperLargeNet() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getLargeNet() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getNorthboundNet() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMarginBalance() {
        return null;
    }
    
    public final int getNewsHeatScore() {
        return 0;
    }
    
    public final boolean isLimitUp() {
        return false;
    }
    
    public final boolean isLimitDown() {
        return false;
    }
    
    public final int getScore() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final boolean component7() {
        return false;
    }
    
    public final boolean component8() {
        return false;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.stockanalyzer.data.model.SentimentData copy(@org.jetbrains.annotations.Nullable()
    java.lang.Double mainNetInflow, @org.jetbrains.annotations.Nullable()
    java.lang.Double superLargeNet, @org.jetbrains.annotations.Nullable()
    java.lang.Double largeNet, @org.jetbrains.annotations.Nullable()
    java.lang.Double northboundNet, @org.jetbrains.annotations.Nullable()
    java.lang.Double marginBalance, int newsHeatScore, boolean isLimitUp, boolean isLimitDown, int score) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}