package com.stockanalyzer.ui.watchlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.stockanalyzer.R
import com.stockanalyzer.databinding.FragmentWatchlistBinding
import com.stockanalyzer.ui.detail.StockDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchlistFragment : Fragment(R.layout.fragment_watchlist) {

    private var _binding: FragmentWatchlistBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WatchlistViewModel by viewModels()
    private lateinit var adapter: WatchlistAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWatchlistBinding.bind(view)

        adapter = WatchlistAdapter(
            onClick = { entity ->
                startActivity(Intent(requireContext(), StockDetailActivity::class.java).apply {
                    putExtra("code", entity.code)
                    putExtra("name", entity.name)
                    putExtra("market", entity.market)
                })
            },
            onLongClick = { entity ->
                AlertDialog.Builder(requireContext())
                    .setTitle("删除自选")
                    .setMessage("确认从自选股中移除 ${entity.name}？")
                    .setPositiveButton("删除") { _, _ -> viewModel.removeStock(entity.id) }
                    .setNegativeButton("取消", null)
                    .show()
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.watchlist.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.layoutEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            binding.recyclerView.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
