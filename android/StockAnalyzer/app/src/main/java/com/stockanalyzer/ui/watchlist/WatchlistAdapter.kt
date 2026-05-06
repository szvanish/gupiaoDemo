package com.stockanalyzer.ui.watchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stockanalyzer.R
import com.stockanalyzer.data.db.WatchlistEntity
import com.stockanalyzer.data.model.StockQuote
import com.stockanalyzer.databinding.ItemWatchlistStockBinding

class WatchlistAdapter(
    private val onClick: (WatchlistEntity) -> Unit,
    private val onLongClick: (WatchlistEntity) -> Unit
) : ListAdapter<WatchlistEntity, WatchlistAdapter.VH>(DIFF) {

    private var quotes: Map<String, StockQuote> = emptyMap()

    fun updateQuotes(quotes: Map<String, StockQuote>) {
        this.quotes = quotes
        notifyDataSetChanged()
    }

    inner class VH(private val binding: ItemWatchlistStockBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WatchlistEntity) {
            binding.tvName.text = item.name
            binding.tvCodeMarket.text = "${item.code}  ${item.market}股"

            val quote = quotes["${item.market}:${item.code}"]
            if (quote != null) {
                binding.tvPrice.text = "%.2f".format(quote.price)
                val sign = if (quote.changePct >= 0) "+" else ""
                binding.tvChangePct.text = "$sign${"%.2f".format(quote.changePct)}%"
                val bgRes = if (quote.changePct >= 0) R.drawable.change_badge_bg
                            else R.drawable.change_badge_bg_green
                binding.tvChangePct.setBackgroundResource(bgRes)
                val priceColor = if (quote.changePct >= 0) 0xFFEF5350.toInt() else 0xFF26A69A.toInt()
                binding.tvPrice.setTextColor(priceColor)
            } else {
                binding.tvPrice.text = "--"
                binding.tvPrice.setTextColor(0xFFF1F5F9.toInt())
                binding.tvChangePct.text = "--"
                binding.tvChangePct.setBackgroundResource(R.drawable.change_badge_bg)
            }

            binding.root.setOnClickListener { onClick(item) }
            binding.root.setOnLongClickListener { onLongClick(item); true }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemWatchlistStockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<WatchlistEntity>() {
            override fun areItemsTheSame(a: WatchlistEntity, b: WatchlistEntity) = a.id == b.id
            override fun areContentsTheSame(a: WatchlistEntity, b: WatchlistEntity) = a == b
        }
    }
}
