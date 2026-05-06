package com.stockanalyzer.ui.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.stockanalyzer.R
import com.stockanalyzer.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news) {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var adapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsBinding.bind(view)

        adapter = NewsAdapter { item ->
            val intent = Intent(requireContext(), NewsDetailActivity::class.java).apply {
                putExtra(NewsDetailActivity.EXTRA_TITLE,   item.title)
                putExtra(NewsDetailActivity.EXTRA_SOURCE,  item.source)
                putExtra(NewsDetailActivity.EXTRA_TIME,    formatTime(item.publishedAt))
                putExtra(NewsDetailActivity.EXTRA_CONTENT, item.summary ?: "")
                putExtra(NewsDetailActivity.EXTRA_URL,     item.url)
            }
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        listOf("全市场热点", "自选股追踪").forEach {
            binding.tabNews.addTab(binding.tabNews.newTab().setText(it))
        }
        binding.tabNews.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.currentTab = tab.position
                viewModel.loadHotNews()
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })

        binding.swipeRefresh.setOnRefreshListener { viewModel.loadHotNews() }
        viewModel.news.observe(viewLifecycleOwner) { adapter.submitList(it) }
        viewModel.isLoading.observe(viewLifecycleOwner) { binding.swipeRefresh.isRefreshing = it }

        viewModel.loadHotNews()
    }

    private fun formatTime(raw: String): String {
        if (raw.length == 8 && raw.all { it.isDigit() }) {
            return "${raw.substring(0, 4)}-${raw.substring(4, 6)}-${raw.substring(6, 8)}"
        }
        return raw.take(16)
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
