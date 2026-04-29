package com.stockanalyzer.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B+\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000bJ\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000bJ\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\bH\u00c6\u0003J<\u0010\u0016\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001\u00a2\u0006\u0002\u0010\u0017J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001b\u001a\u00020\bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u0006H\u00d6\u0001R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\f\u001a\u0004\b\u000f\u0010\u000bR\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001d"}, d2 = {"Lcom/stockanalyzer/data/model/ChipData;", "", "institutionHoldingPct", "", "shareholderCountChangePct", "top10HolderChange", "", "score", "", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/String;I)V", "getInstitutionHoldingPct", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getScore", "()I", "getShareholderCountChangePct", "getTop10HolderChange", "()Ljava/lang/String;", "component1", "component2", "component3", "component4", "copy", "(Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/String;I)Lcom/stockanalyzer/data/model/ChipData;", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class ChipData {
    @com.google.gson.annotations.SerializedName(value = "institution_holding_pct")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double institutionHoldingPct = null;
    @com.google.gson.annotations.SerializedName(value = "shareholder_count_change_pct")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double shareholderCountChangePct = null;
    @com.google.gson.annotations.SerializedName(value = "top10_holder_change")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String top10HolderChange = null;
    private final int score = 0;
    
    public ChipData(@org.jetbrains.annotations.Nullable()
    java.lang.Double institutionHoldingPct, @org.jetbrains.annotations.Nullable()
    java.lang.Double shareholderCountChangePct, @org.jetbrains.annotations.Nullable()
    java.lang.String top10HolderChange, int score) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getInstitutionHoldingPct() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getShareholderCountChangePct() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTop10HolderChange() {
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
    public final java.lang.String component3() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.stockanalyzer.data.model.ChipData copy(@org.jetbrains.annotations.Nullable()
    java.lang.Double institutionHoldingPct, @org.jetbrains.annotations.Nullable()
    java.lang.Double shareholderCountChangePct, @org.jetbrains.annotations.Nullable()
    java.lang.String top10HolderChange, int score) {
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