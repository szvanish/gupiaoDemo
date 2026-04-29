package com.stockanalyzer.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.stockanalyzer.R
import com.stockanalyzer.databinding.FragmentSearchBinding
import com.stockanalyzer.ui.detail.StockDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        adapter = SearchAdapter { result ->
            startActivity(Intent(requireContext(), StockDetailActivity::class.java).apply {
                putExtra("code", result.code)
                putExtra("name", result.name)
                putExtra("market", result.market)
            })
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        listOf("A股", "港股", "美股").forEach {
            binding.tabMarket.addTab(binding.tabMarket.newTab().setText(it))
        }
        binding.tabMarket.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.currentMarket = listOf("A", "HK", "US")[tab.position]
                viewModel.search(binding.etSearch.text.toString())
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) { viewModel.search(s.toString()) }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit
        })

        viewModel.results.observe(viewLifecycleOwner) { results ->
            adapter.submitList(results)
            binding.layoutEmpty.visibility = if (results.isEmpty()) View.VISIBLE else View.GONE
            binding.recyclerView.visibility = if (results.isEmpty()) View.GONE else View.VISIBLE
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
