package com.stockanalyzer.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.tabs.TabLayout
import com.stockanalyzer.data.model.AIReport
import com.stockanalyzer.data.model.KLineBar
import com.stockanalyzer.data.model.StockAnalysis
import com.stockanalyzer.data.model.StockQuote
import com.stockanalyzer.databinding.ActivityStockDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStockDetailBinding
    private val viewModel: StockDetailViewModel by viewModels()
    private lateinit var code: String
    private lateinit var name: String
    private lateinit var market: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        code = intent.getStringExtra("code") ?: return
        name = intent.getStringExtra("name") ?: code
        market = intent.getStringExtra("market") ?: "A"

        binding.tvStockName.text = name
        binding.tvStockCode.text = "$code · ${market}股"

        setupPeriodTabs()
        setupObservers()

        viewModel.load(code, market)
        viewModel.loadReport(code, market)
    }

    private fun setupPeriodTabs() {
        listOf("日K", "周K", "月K").forEach {
            binding.tabPeriod.addTab(binding.tabPeriod.newTab().setText(it))
        }
        binding.tabPeriod.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val period = listOf("day", "week", "month")[tab.position]
                viewModel.load(code, market, period)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
    }

    private fun setupObservers() {
        viewModel.quote.observe(this) { renderQuote(it) }
        viewModel.kline.observe(this) { renderKline(it) }
        viewModel.analysis.observe(this) { renderRadar(it) }
        viewModel.report.observe(this) { it?.let { r -> renderReport(r) } }
        viewModel.isLoadingReport.observe(this) { loading ->
            binding.progressReport.visibility = if (loading) View.VISIBLE else View.GONE
        }
        viewModel.isInWatchlist.observe(this) { inList ->
            binding.btnWatchlist.text = if (inList) "已自选" else "+ 自选"
        }
        binding.btnWatchlist.setOnClickListener { viewModel.toggleWatchlist(code, name, market) }
    }

    private fun renderQuote(q: StockQuote) {
        binding.tvPrice.text = "%.2f".format(q.price)
        val sign = if (q.changePct >= 0) "+" else ""
        binding.tvChange.text = "$sign${"%.2f".format(q.change)}  $sign${"%.2f".format(q.changePct)}%"
        val color = if (q.changePct >= 0) 0xFFE53935.toInt() else 0xFF43A047.toInt()
        binding.tvPrice.setTextColor(color)
        binding.tvChange.setTextColor(color)
        binding.tvVolume.text = "成交量: ${"%.0f".format(q.volume / 10000)}万"
        binding.tvAmount.text = "成交额: ${"%.2f".format(q.amount / 1e8)}亿"
    }

    private fun renderKline(bars: List<KLineBar>) {
        if (bars.isEmpty()) return
        val entries = bars.mapIndexed { i, bar ->
            CandleEntry(i.toFloat(), bar.high.toFloat(), bar.low.toFloat(),
                bar.open.toFloat(), bar.close.toFloat())
        }
        val dataset = CandleDataSet(entries, "").apply {
            increasingColor = 0xFFE53935.toInt()
            increasingPaintStyle = android.graphics.Paint.Style.FILL
            decreasingColor = 0xFF43A047.toInt()
            shadowColor = 0xFF888888.toInt()
            setDrawValues(false)
        }
        binding.candleChart.apply {
            data = CandleData(dataset)
            description.isEnabled = false
            legend.isEnabled = false
            xAxis.setDrawLabels(false)
            invalidate()
        }
    }

    private fun renderRadar(analysis: StockAnalysis) {
        val scores = listOf(
            analysis.technical.score,
            analysis.fundamental.score,
            analysis.valuation.score,
            analysis.sentiment.score,
            analysis.chip.score,
            analysis.macro.score,
        )
        val entries = scores.map { RadarEntry(it.toFloat()) }
        val dataset = RadarDataSet(entries, "综合评分").apply {
            color = 0xFF1565C0.toInt()
            fillColor = 0x441565C0.toInt()
            setDrawFilled(true)
            lineWidth = 2f
        }
        val radarLabels = listOf("技术", "基本", "估值", "情绪", "筹码", "宏观")
        binding.radarChart.apply {
            data = RadarData(dataset)
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float) =
                    radarLabels.getOrElse(value.toInt()) { "" }
            }
            yAxis.axisMinimum = 0f
            yAxis.axisMaximum = 100f
            description.isEnabled = false
            invalidate()
        }
    }

    private fun renderReport(r: AIReport) {
        val ratingColor = when (r.rating) {
            "强烈买入", "买入" -> 0xFFE53935.toInt()
            "强烈卖出", "卖出" -> 0xFF43A047.toInt()
            else -> 0xFF1565C0.toInt()
        }
        binding.tvRating.text = "【${r.rating}】"
        binding.tvRating.setTextColor(ratingColor)
        r.targetPriceLow?.let { low ->
            r.targetPriceHigh?.let { high ->
                binding.tvRating.append("  目标价: ${"%.2f".format(low)}-${"%.2f".format(high)}")
            }
        }
        binding.tvSummary.text = r.summary
        binding.tvReportDetail.text = buildString {
            append("【行情复盘】${r.marketReview}\n\n")
            append("【基本面】${r.fundamentalAnalysis}\n\n")
            append("【技术面】${r.technicalAnalysis}\n\n")
            append("【估值分析】${r.valuationAnalysis}\n\n")
            append("【情绪与资金】${r.sentimentAnalysis}")
        }
        binding.tvRisks.text = "【风险提示】\n" + r.risks.joinToString("\n") { "• $it" }
        binding.tvRecommendation.text = "【操作建议】${r.recommendation}"
    }
}
