package com.stockanalyzer.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stockanalyzer.data.model.NewsItem
import com.stockanalyzer.databinding.ItemNewsBinding

class NewsAdapter(private val onClick: (NewsItem) -> Unit) :
    ListAdapter<NewsItem, NewsAdapter.VH>(DIFF) {

    inner class VH(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsItem) {
            binding.tvTitle.text = item.title
            binding.tvSource.text = item.source
            binding.tvTime.text = formatTime(item.publishedAt)
            if (!item.summary.isNullOrEmpty()) {
                binding.tvSummary.text = item.summary
                binding.tvSummary.visibility = View.VISIBLE
            } else {
                binding.tvSummary.visibility = View.GONE
            }
            binding.root.setOnClickListener { onClick(item) }
        }

        private fun formatTime(raw: String): String {
            if (raw.length == 8 && raw.all { it.isDigit() }) {
                return "${raw.substring(0, 4)}-${raw.substring(4, 6)}-${raw.substring(6, 8)}"
            }
            return raw.take(16)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<NewsItem>() {
            override fun areItemsTheSame(a: NewsItem, b: NewsItem) = a.id == b.id
            override fun areContentsTheSame(a: NewsItem, b: NewsItem) = a == b
        }
    }
}
