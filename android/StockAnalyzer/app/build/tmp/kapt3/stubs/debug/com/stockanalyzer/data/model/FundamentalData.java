package com.stockanalyzer.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u000e\n\u0002\u0010\b\n\u0002\b%\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0099\u0001\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\u0002\u0010\u0013J\u0010\u0010&\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010\'\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010(\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010)\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010*\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010+\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\t\u0010,\u001a\u00020\u0012H\u00c6\u0003J\u0010\u0010-\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010.\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010/\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u00100\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u00101\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u00102\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u00103\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u00104\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u00c0\u0001\u00105\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u00c6\u0001\u00a2\u0006\u0002\u00106J\u0013\u00107\u001a\u0002082\b\u00109\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010:\u001a\u00020\u0012H\u00d6\u0001J\t\u0010;\u001a\u00020<H\u00d6\u0001R\u001a\u0010\r\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\f\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0017\u0010\u0015R\u001a\u0010\u0010\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0018\u0010\u0015R\u0015\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0019\u0010\u0015R\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u001a\u0010\u0015R\u001a\u0010\n\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u001b\u0010\u0015R\u001a\u0010\u000b\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u001c\u0010\u0015R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u001d\u0010\u0015R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u001e\u0010\u0015R\u001a\u0010\t\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u001f\u0010\u0015R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b \u0010\u0015R\u001a\u0010\b\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b!\u0010\u0015R\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\"\u0010\u0015R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b#\u0010\u0015R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%\u00a8\u0006="}, d2 = {"Lcom/stockanalyzer/data/model/FundamentalData;", "", "peTtm", "", "pb", "ps", "roe", "roa", "revenueGrowthYoy", "profitGrowthYoy", "grossMargin", "netMargin", "debtRatio", "currentRatio", "freeCashFlow", "eps", "dividendYield", "score", "", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;I)V", "getCurrentRatio", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getDebtRatio", "getDividendYield", "getEps", "getFreeCashFlow", "getGrossMargin", "getNetMargin", "getPb", "getPeTtm", "getProfitGrowthYoy", "getPs", "getRevenueGrowthYoy", "getRoa", "getRoe", "getScore", "()I", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;I)Lcom/stockanalyzer/data/model/FundamentalData;", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class FundamentalData {
    @com.google.gson.annotations.SerializedName(value = "pe_ttm")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double peTtm = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double pb = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double ps = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double roe = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double roa = null;
    @com.google.gson.annotations.SerializedName(value = "revenue_growth_yoy")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double revenueGrowthYoy = null;
    @com.google.gson.annotations.SerializedName(value = "profit_growth_yoy")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double profitGrowthYoy = null;
    @com.google.gson.annotations.SerializedName(value = "gross_margin")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double grossMargin = null;
    @com.google.gson.annotations.SerializedName(value = "net_margin")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double netMargin = null;
    @com.google.gson.annotations.SerializedName(value = "debt_ratio")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double debtRatio = null;
    @com.google.gson.annotations.SerializedName(value = "current_ratio")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double currentRatio = null;
    @com.google.gson.annotations.SerializedName(value = "free_cash_flow")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double freeCashFlow = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double eps = null;
    @com.google.gson.annotations.SerializedName(value = "dividend_yield")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double dividendYield = null;
    private final int score = 0;
    
    public FundamentalData(@org.jetbrains.annotations.Nullable()
    java.lang.Double peTtm, @org.jetbrains.annotations.Nullable()
    java.lang.Double pb, @org.jetbrains.annotations.Nullable()
    java.lang.Double ps, @org.jetbrains.annotations.Nullable()
    java.lang.Double roe, @org.jetbrains.annotations.Nullable()
    java.lang.Double roa, @org.jetbrains.annotations.Nullable()
    java.lang.Double revenueGrowthYoy, @org.jetbrains.annotations.Nullable()
    java.lang.Double profitGrowthYoy, @org.jetbrains.annotations.Nullable()
    java.lang.Double grossMargin, @org.jetbrains.annotations.Nullable()
    java.lang.Double netMargin, @org.jetbrains.annotations.Nullable()
    java.lang.Double debtRatio, @org.jetbrains.annotations.Nullable()
    java.lang.Double currentRatio, @org.jetbrains.annotations.Nullable()
    java.lang.Double freeCashFlow, @org.jetbrains.annotations.Nullable()
    java.lang.Double eps, @org.jetbrains.annotations.Nullable()
    java.lang.Double dividendYield, int score) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPeTtm() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPb() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPs() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getRoe() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getRoa() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getRevenueGrowthYoy() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getProfitGrowthYoy() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getGrossMargin() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getNetMargin() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getDebtRatio() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getCurrentRatio() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getFreeCashFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getEps() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getDividendYield() {
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
    
    public final int component15() {
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
    public final com.stockanalyzer.data.model.FundamentalData copy(@org.jetbrains.annotations.Nullable()
    java.lang.Double peTtm, @org.jetbrains.annotations.Nullable()
    java.lang.Double pb, @org.jetbrains.annotations.Nullable()
    java.lang.Double ps, @org.jetbrains.annotations.Nullable()
    java.lang.Double roe, @org.jetbrains.annotations.Nullable()
    java.lang.Double roa, @org.jetbrains.annotations.Nullable()
    java.lang.Double revenueGrowthYoy, @org.jetbrains.annotations.Nullable()
    java.lang.Double profitGrowthYoy, @org.jetbrains.annotations.Nullable()
    java.lang.Double grossMargin, @org.jetbrains.annotations.Nullable()
    java.lang.Double netMargin, @org.jetbrains.annotations.Nullable()
    java.lang.Double debtRatio, @org.jetbrains.annotations.Nullable()
    java.lang.Double currentRatio, @org.jetbrains.annotations.Nullable()
    java.lang.Double freeCashFlow, @org.jetbrains.annotations.Nullable()
    java.lang.Double eps, @org.jetbrains.annotations.Nullable()
    java.lang.Double dividendYield, int score) {
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