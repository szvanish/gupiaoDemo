package com.stockanalyzer.ui.watchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stockanalyzer.data.db.WatchlistEntity
import com.stockanalyzer.databinding.ItemWatchlistStockBinding

class WatchlistAdapter(
    private val onClick: (WatchlistEntity) -> Unit,
    private val onLongClick: (WatchlistEntity) -> Unit
) : ListAdapter<WatchlistEntity, WatchlistAdapter.VH>(DIFF) {

    inner class VH(private val binding: ItemWatchlistStockBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WatchlistEntity) {
            binding.tvName.text = item.name
            binding.tvCodeMarket.text = "${item.code}  ${item.market}股"
            binding.tvPrice.text = "--"
            binding.tvChangePct.text = "--"
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
