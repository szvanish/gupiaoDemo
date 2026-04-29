package com.stockanalyzer.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0012\n\u0002\u0010\b\n\u0002\b-\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u00c1\u0001\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\u0002\u0010\u0017J\u0010\u0010.\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u0010/\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u00100\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u00101\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u00102\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u00103\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u00104\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u00105\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u00106\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u00107\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\t\u00108\u001a\u00020\u0016H\u00c6\u0003J\u0010\u00109\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u0010:\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u0010;\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u0010<\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u0010=\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u0010>\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u0010?\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0010\u0010@\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u00f0\u0001\u0010A\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u00c6\u0001\u00a2\u0006\u0002\u0010BJ\u0013\u0010C\u001a\u00020D2\b\u0010E\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010F\u001a\u00020\u0016H\u00d6\u0001J\t\u0010G\u001a\u00020HH\u00d6\u0001R\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u0011\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u001b\u0010\u0019R\u001a\u0010\u0010\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u001c\u0010\u0019R\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u001d\u0010\u0019R\u001a\u0010\u000f\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u001e\u0010\u0019R\u001a\u0010\r\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u001f\u0010\u0019R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b \u0010\u0019R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b!\u0010\u0019R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\"\u0010\u0019R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b#\u0010\u0019R\u001a\u0010\t\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b$\u0010\u0019R\u001a\u0010\b\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b%\u0010\u0019R\u001a\u0010\u0007\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b&\u0010\u0019R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\'\u0010\u0019R\u0015\u0010\f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b(\u0010\u0019R\u0015\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b)\u0010\u0019R\u0011\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b,\u0010\u0019R\u001a\u0010\u0013\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b-\u0010\u0019\u00a8\u0006I"}, d2 = {"Lcom/stockanalyzer/data/model/TechnicalIndicators;", "", "ma5", "", "ma10", "ma20", "ma60", "macdDif", "macdDea", "macdBar", "rsi6", "rsi12", "rsi24", "kdjK", "kdjD", "kdjJ", "bollUpper", "bollMid", "bollLower", "volumeRatio", "turnoverRate", "score", "", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;I)V", "getBollLower", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getBollMid", "getBollUpper", "getKdjD", "getKdjJ", "getKdjK", "getMa10", "getMa20", "getMa5", "getMa60", "getMacdBar", "getMacdDea", "getMacdDif", "getRsi12", "getRsi24", "getRsi6", "getScore", "()I", "getTurnoverRate", "getVolumeRatio", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;I)Lcom/stockanalyzer/data/model/TechnicalIndicators;", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class TechnicalIndicators {
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double ma5 = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double ma10 = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double ma20 = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double ma60 = null;
    @com.google.gson.annotations.SerializedName(value = "macd_dif")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double macdDif = null;
    @com.google.gson.annotations.SerializedName(value = "macd_dea")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double macdDea = null;
    @com.google.gson.annotations.SerializedName(value = "macd_bar")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double macdBar = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double rsi6 = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double rsi12 = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double rsi24 = null;
    @com.google.gson.annotations.SerializedName(value = "kdj_k")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double kdjK = null;
    @com.google.gson.annotations.SerializedName(value = "kdj_d")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double kdjD = null;
    @com.google.gson.annotations.SerializedName(value = "kdj_j")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double kdjJ = null;
    @com.google.gson.annotations.SerializedName(value = "boll_upper")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double bollUpper = null;
    @com.google.gson.annotations.SerializedName(value = "boll_mid")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double bollMid = null;
    @com.google.gson.annotations.SerializedName(value = "boll_lower")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double bollLower = null;
    @com.google.gson.annotations.SerializedName(value = "volume_ratio")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double volumeRatio = null;
    @com.google.gson.annotations.SerializedName(value = "turnover_rate")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double turnoverRate = null;
    private final int score = 0;
    
    public TechnicalIndicators(@org.jetbrains.annotations.Nullable()
    java.lang.Double ma5, @org.jetbrains.annotations.Nullable()
    java.lang.Double ma10, @org.jetbrains.annotations.Nullable()
    java.lang.Double ma20, @org.jetbrains.annotations.Nullable()
    java.lang.Double ma60, @org.jetbrains.annotations.Nullable()
    java.lang.Double macdDif, @org.jetbrains.annotations.Nullable()
    java.lang.Double macdDea, @org.jetbrains.annotations.Nullable()
    java.lang.Double macdBar, @org.jetbrains.annotations.Nullable()
    java.lang.Double rsi6, @org.jetbrains.annotations.Nullable()
    java.lang.Double rsi12, @org.jetbrains.annotations.Nullable()
    java.lang.Double rsi24, @org.jetbrains.annotations.Nullable()
    java.lang.Double kdjK, @org.jetbrains.annotations.Nullable()
    java.lang.Double kdjD, @org.jetbrains.annotations.Nullable()
    java.lang.Double kdjJ, @org.jetbrains.annotations.Nullable()
    java.lang.Double bollUpper, @org.jetbrains.annotations.Nullable()
    java.lang.Double bollMid, @org.jetbrains.annotations.Nullable()
    java.lang.Double bollLower, @org.jetbrains.annotations.Nullable()
    java.lang.Double volumeRatio, @org.jetbrains.annotations.Nullable()
    java.lang.Double turnoverRate, int score) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMa5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMa10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMa20() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMa60() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMacdDif() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMacdDea() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getMacdBar() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getRsi6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getRsi12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getRsi24() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getKdjK() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getKdjD() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getKdjJ() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getBollUpper() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getBollMid() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getBollLower() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getVolumeRatio() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getTurnoverRate() {
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
    public final java.lang.Double component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component12() {
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component15() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component16() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component17() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component18() {
        return null;
    }
    
    public final int component19() {
        return 0;
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.stockanalyzer.data.model.TechnicalIndicators copy(@org.jetbrains.annotations.Nullable()
    java.lang.Double ma5, @org.jetbrains.annotations.Nullable()
    java.lang.Double ma10, @org.jetbrains.annotations.Nullable()
    java.lang.Double ma20, @org.jetbrains.annotations.Nullable()
    java.lang.Double ma60, @org.jetbrains.annotations.Nullable()
    java.lang.Double macdDif, @org.jetbrains.annotations.Nullable()
    java.lang.Double macdDea, @org.jetbrains.annotations.Nullable()
    java.lang.Double macdBar, @org.jetbrains.annotations.Nullable()
    java.lang.Double rsi6, @org.jetbrains.annotations.Nullable()
    java.lang.Double rsi12, @org.jetbrains.annotations.Nullable()
    java.lang.Double rsi24, @org.jetbrains.annotations.Nullable()
    java.lang.Double kdjK, @org.jetbrains.annotations.Nullable()
    java.lang.Double kdjD, @org.jetbrains.annotations.Nullable()
    java.lang.Double kdjJ, @org.jetbrains.annotations.Nullable()
    java.lang.Double bollUpper, @org.jetbrains.annotations.Nullable()
    java.lang.Double bollMid, @org.jetbrains.annotations.Nullable()
    java.lang.Double bollLower, @org.jetbrains.annotations.Nullable()
    java.lang.Double volumeRatio, @org.jetbrains.annotations.Nullable()
    java.lang.Double turnoverRate, int score) {
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