package com.stockanalyzer.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B?\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\fJ\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\fJ\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\fJ\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\fJ\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0019\u001a\u00020\tH\u00c6\u0003JT\u0010\u001a\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\b\u001a\u00020\tH\u00c6\u0001\u00a2\u0006\u0002\u0010\u001bJ\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020\tH\u00d6\u0001J\t\u0010 \u001a\u00020!H\u00d6\u0001R\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000e\u0010\fR\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000f\u0010\fR\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u0010\u0010\fR\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u0011\u0010\fR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\""}, d2 = {"Lcom/stockanalyzer/data/model/ValuationData;", "", "industryAvgPe", "", "industryAvgPb", "pePercentile", "pbPercentile", "peg", "score", "", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;I)V", "getIndustryAvgPb", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getIndustryAvgPe", "getPbPercentile", "getPePercentile", "getPeg", "getScore", "()I", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;I)Lcom/stockanalyzer/data/model/ValuationData;", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class ValuationData {
    @com.google.gson.annotations.SerializedName(value = "industry_avg_pe")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double industryAvgPe = null;
    @com.google.gson.annotations.SerializedName(value = "industry_avg_pb")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double industryAvgPb = null;
    @com.google.gson.annotations.SerializedName(value = "pe_percentile")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double pePercentile = null;
    @com.google.gson.annotations.SerializedName(value = "pb_percentile")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double pbPercentile = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double peg = null;
    private final int score = 0;
    
    public ValuationData(@org.jetbrains.annotations.Nullable()
    java.lang.Double industryAvgPe, @org.jetbrains.annotations.Nullable()
    java.lang.Double industryAvgPb, @org.jetbrains.annotations.Nullable()
    java.lang.Double pePercentile, @org.jetbrains.annotations.Nullable()
    java.lang.Double pbPercentile, @org.jetbrains.annotations.Nullable()
    java.lang.Double peg, int score) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getIndustryAvgPe() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getIndustryAvgPb() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPePercentile() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPbPercentile() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPeg() {
        return null;
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
    
    @org.jetbrains.annotations.NotNull()
    public final com.stockanalyzer.data.model.ValuationData copy(@org.jetbrains.annotations.Nullable()
    java.lang.Double industryAvgPe, @org.jetbrains.annotations.Nullable()
    java.lang.Double industryAvgPb, @org.jetbrains.annotations.Nullable()
    java.lang.Double pePercentile, @org.jetbrains.annotations.Nullable()
    java.lang.Double pbPercentile, @org.jetbrains.annotations.Nullable()
    java.lang.Double peg, int score) {
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