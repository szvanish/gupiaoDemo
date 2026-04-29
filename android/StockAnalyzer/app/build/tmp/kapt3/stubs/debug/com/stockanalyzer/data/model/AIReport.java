package com.stockanalyzer.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b+\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u008f\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\r\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0011\u0012\u0006\u0010\u0013\u001a\u00020\u0003\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\u0002\u0010\u0016J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00030\rH\u00c6\u0003J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\u0010\u00100\u001a\u0004\u0018\u00010\u0011H\u00c6\u0003\u00a2\u0006\u0002\u0010\'J\u0010\u00101\u001a\u0004\u0018\u00010\u0011H\u00c6\u0003\u00a2\u0006\u0002\u0010\'J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\u0015H\u00c6\u0003J\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\t\u00105\u001a\u00020\u0003H\u00c6\u0003J\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\u0003H\u00c6\u0003J\t\u00108\u001a\u00020\u0003H\u00c6\u0003J\t\u00109\u001a\u00020\u0003H\u00c6\u0003J\t\u0010:\u001a\u00020\u0003H\u00c6\u0003J\t\u0010;\u001a\u00020\u0003H\u00c6\u0003J\u00b8\u0001\u0010<\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\r2\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00112\b\b\u0002\u0010\u0013\u001a\u00020\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u00c6\u0001\u00a2\u0006\u0002\u0010=J\u0013\u0010>\u001a\u00020\u00152\b\u0010?\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010@\u001a\u00020AH\u00d6\u0001J\t\u0010B\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u0016\u0010\u0013\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001aR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001aR\u0016\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001aR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001aR\u0011\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001aR\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001aR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0016\u0010\u000b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001aR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001aR\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u00118\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010(\u001a\u0004\b&\u0010\'R\u001a\u0010\u0010\u001a\u0004\u0018\u00010\u00118\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010(\u001a\u0004\b)\u0010\'R\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001aR\u0016\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001a\u00a8\u0006C"}, d2 = {"Lcom/stockanalyzer/data/model/AIReport;", "", "code", "", "name", "market", "summary", "marketReview", "fundamentalAnalysis", "technicalAnalysis", "valuationAnalysis", "sentimentAnalysis", "risks", "", "recommendation", "rating", "targetPriceLow", "", "targetPriceHigh", "generatedAt", "cached", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/String;Z)V", "getCached", "()Z", "getCode", "()Ljava/lang/String;", "getFundamentalAnalysis", "getGeneratedAt", "getMarket", "getMarketReview", "getName", "getRating", "getRecommendation", "getRisks", "()Ljava/util/List;", "getSentimentAnalysis", "getSummary", "getTargetPriceHigh", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getTargetPriceLow", "getTechnicalAnalysis", "getValuationAnalysis", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/String;Z)Lcom/stockanalyzer/data/model/AIReport;", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class AIReport {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String code = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String market = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String summary = null;
    @com.google.gson.annotations.SerializedName(value = "market_review")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String marketReview = null;
    @com.google.gson.annotations.SerializedName(value = "fundamental_analysis")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String fundamentalAnalysis = null;
    @com.google.gson.annotations.SerializedName(value = "technical_analysis")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String technicalAnalysis = null;
    @com.google.gson.annotations.SerializedName(value = "valuation_analysis")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String valuationAnalysis = null;
    @com.google.gson.annotations.SerializedName(value = "sentiment_analysis")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String sentimentAnalysis = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> risks = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String recommendation = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String rating = null;
    @com.google.gson.annotations.SerializedName(value = "target_price_low")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double targetPriceLow = null;
    @com.google.gson.annotations.SerializedName(value = "target_price_high")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double targetPriceHigh = null;
    @com.google.gson.annotations.SerializedName(value = "generated_at")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String generatedAt = null;
    private final boolean cached = false;
    
    public AIReport(@org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String market, @org.jetbrains.annotations.NotNull()
    java.lang.String summary, @org.jetbrains.annotations.NotNull()
    java.lang.String marketReview, @org.jetbrains.annotations.NotNull()
    java.lang.String fundamentalAnalysis, @org.jetbrains.annotations.NotNull()
    java.lang.String technicalAnalysis, @org.jetbrains.annotations.NotNull()
    java.lang.String valuationAnalysis, @org.jetbrains.annotations.NotNull()
    java.lang.String sentimentAnalysis, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> risks, @org.jetbrains.annotations.NotNull()
    java.lang.String recommendation, @org.jetbrains.annotations.NotNull()
    java.lang.String rating, @org.jetbrains.annotations.Nullable()
    java.lang.Double targetPriceLow, @org.jetbrains.annotations.Nullable()
    java.lang.Double targetPriceHigh, @org.jetbrains.annotations.NotNull()
    java.lang.String generatedAt, boolean cached) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMarket() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSummary() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMarketReview() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFundamentalAnalysis() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTechnicalAnalysis() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getValuationAnalysis() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSentimentAnalysis() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getRisks() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRecommendation() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRating() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getTargetPriceLow() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getTargetPriceHigh() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGeneratedAt() {
        return null;
    }
    
    public final boolean getCached() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component15() {
        return null;
    }
    
    public final boolean component16() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.stockanalyzer.data.model.AIReport copy(@org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String market, @org.jetbrains.annotations.NotNull()
    java.lang.String summary, @org.jetbrains.annotations.NotNull()
    java.lang.String marketReview, @org.jetbrains.annotations.NotNull()
    java.lang.String fundamentalAnalysis, @org.jetbrains.annotations.NotNull()
    java.lang.String technicalAnalysis, @org.jetbrains.annotations.NotNull()
    java.lang.String valuationAnalysis, @org.jetbrains.annotations.NotNull()
    java.lang.String sentimentAnalysis, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> risks, @org.jetbrains.annotations.NotNull()
    java.lang.String recommendation, @org.jetbrains.annotations.NotNull()
    java.lang.String rating, @org.jetbrains.annotations.Nullable()
    java.lang.Double targetPriceLow, @org.jetbrains.annotations.Nullable()
    java.lang.Double targetPriceHigh, @org.jetbrains.annotations.NotNull()
    java.lang.String generatedAt, boolean cached) {
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