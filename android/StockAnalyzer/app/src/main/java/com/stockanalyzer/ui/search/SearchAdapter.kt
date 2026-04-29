package com.stockanalyzer.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stockanalyzer.data.model.StockSearchResult
import com.stockanalyzer.databinding.ItemSearchResultBinding

class SearchAdapter(
    private val onClick: (StockSearchResult) -> Unit
) : ListAdapter<StockSearchResult, SearchAdapter.VH>(DIFF) {

    inner class VH(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StockSearchResult) {
            binding.tvName.text = item.name
            binding.tvCode.text = "${item.code}  ${item.market}股"
            binding.tvPrice.text = item.price?.let { "%.2f".format(it) } ?: "--"
            val pct = item.changePct
            if (pct != null) {
                val sign = if (pct >= 0) "+" else ""
                binding.tvChangePct.text = "$sign${"%.2f".format(pct)}%"
                binding.tvChangePct.setTextColor(if (pct >= 0) 0xFFE53935.toInt() else 0xFF43A047.toInt())
            } else {
                binding.tvChangePct.text = "--"
            }
            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<StockSearchResult>() {
            override fun areItemsTheSame(a: StockSearchResult, b: StockSearchResult) =
                a.code == b.code && a.market == b.market
            override fun areContentsTheSame(a: StockSearchResult, b: StockSearchResult) = a == b
        }
    }
}
