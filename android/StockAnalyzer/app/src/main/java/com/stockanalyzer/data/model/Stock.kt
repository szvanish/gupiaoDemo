package com.stockanalyzer.data.model

import com.google.gson.annotations.SerializedName

data class StockSearchResult(
    val code: String,
    val name: String,
    val market: String,
    val price: Double?,
    @SerializedName("change_pct") val changePct: Double?
)

data class StockQuote(
    val code: String,
    val name: String,
    val market: String,
    val price: Double,
    val change: Double,
    @SerializedName("change_pct") val changePct: Double,
    val volume: Double,
    val amount: Double,
    val high: Double,
    val low: Double,
    val open: Double,
    @SerializedName("prev_close") val prevClose: Double
)

data class KLineBar(
    val date: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Double
)

data class TechnicalIndicators(
    val ma5: Double?, val ma10: Double?, val ma20: Double?, val ma60: Double?,
    @SerializedName("macd_dif") val macdDif: Double?,
    @SerializedName("macd_dea") val macdDea: Double?,
    @SerializedName("macd_bar") val macdBar: Double?,
    val rsi6: Double?, val rsi12: Double?, val rsi24: Double?,
    @SerializedName("kdj_k") val kdjK: Double?,
    @SerializedName("kdj_d") val kdjD: Double?,
    @SerializedName("kdj_j") val kdjJ: Double?,
    @SerializedName("boll_upper") val bollUpper: Double?,
    @SerializedName("boll_mid") val bollMid: Double?,
    @SerializedName("boll_lower") val bollLower: Double?,
    @SerializedName("volume_ratio") val volumeRatio: Double?,
    @SerializedName("turnover_rate") val turnoverRate: Double?,
    val score: Int
)

data class FundamentalData(
    @SerializedName("pe_ttm") val peTtm: Double?,
    val pb: Double?, val ps: Double?,
    val roe: Double?, val roa: Double?,
    @SerializedName("revenue_growth_yoy") val revenueGrowthYoy: Double?,
    @SerializedName("profit_growth_yoy") val profitGrowthYoy: Double?,
    @SerializedName("gross_margin") val grossMargin: Double?,
    @SerializedName("net_margin") val netMargin: Double?,
    @SerializedName("debt_ratio") val debtRatio: Double?,
    @SerializedName("current_ratio") val currentRatio: Double?,
    @SerializedName("free_cash_flow") val freeCashFlow: Double?,
    val eps: Double?,
    @SerializedName("dividend_yield") val dividendYield: Double?,
    val score: Int
)

data class ValuationData(
    @SerializedName("industry_avg_pe") val industryAvgPe: Double?,
    @SerializedName("industry_avg_pb") val industryAvgPb: Double?,
    @SerializedName("pe_percentile") val pePercentile: Double?,
    @SerializedName("pb_percentile") val pbPercentile: Double?,
    val peg: Double?,
    val score: Int
)

data class SentimentData(
    @SerializedName("main_net_inflow") val mainNetInflow: Double?,
    @SerializedName("super_large_net") val superLargeNet: Double?,
    @SerializedName("large_net") val largeNet: Double?,
    @SerializedName("northbound_net") val northboundNet: Double?,
    @SerializedName("margin_balance") val marginBalance: Double?,
    @SerializedName("news_heat_score") val newsHeatScore: Int,
    @SerializedName("is_limit_up") val isLimitUp: Boolean,
    @SerializedName("is_limit_down") val isLimitDown: Boolean,
    val score: Int
)

data class ChipData(
    @SerializedName("institution_holding_pct") val institutionHoldingPct: Double?,
    @SerializedName("shareholder_count_change_pct") val shareholderCountChangePct: Double?,
    @SerializedName("top10_holder_change") val top10HolderChange: String?,
    val score: Int
)

data class MacroData(
    @SerializedName("industry_name") val industryName: String?,
    @SerializedName("industry_change_pct") val industryChangePct: Double?,
    @SerializedName("corr_with_index") val corrWithIndex: Double?,
    @SerializedName("policy_summary") val policySummary: String?,
    val score: Int
)

data class StockAnalysis(
    val code: String, val name: String, val market: String,
    val technical: TechnicalIndicators,
    val fundamental: FundamentalData,
    val valuation: ValuationData,
    val sentiment: SentimentData,
    val chip: ChipData,
    val macro: MacroData
)

data class AIReport(
    val code: String, val name: String, val market: String,
    val summary: String,
    @SerializedName("market_review") val marketReview: String,
    @SerializedName("fundamental_analysis") val fundamentalAnalysis: String,
    @SerializedName("technical_analysis") val technicalAnalysis: String,
    @SerializedName("valuation_analysis") val valuationAnalysis: String,
    @SerializedName("sentiment_analysis") val sentimentAnalysis: String,
    val risks: List<String>,
    val recommendation: String,
    val rating: String,
    @SerializedName("target_price_low") val targetPriceLow: Double?,
    @SerializedName("target_price_high") val targetPriceHigh: Double?,
    @SerializedName("generated_at") val generatedAt: String,
    val cached: Boolean
)
