package com.stockanalyzer.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.stockanalyzer.R
import com.stockanalyzer.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        // Load saved API URL
        val savedUrl = prefs.getString("api_base_url", "http://172.17.6.46:8000") ?: ""
        binding.etApiUrl.setText(savedUrl)

        // Save URL
        binding.btnSaveUrl.setOnClickListener {
            val url = binding.etApiUrl.text.toString().trim().trimEnd('/')
            if (url.isEmpty()) {
                Toast.makeText(requireContext(), "地址不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            prefs.edit().putString("api_base_url", url).apply()
            Toast.makeText(requireContext(), "已保存，立即生效", Toast.LENGTH_SHORT).show()
        }

        // Refresh interval chips
        val savedInterval = prefs.getString("refresh_interval", "30")
        when (savedInterval) {
            "15" -> binding.chip15.isChecked = true
            "60" -> binding.chip60.isChecked = true
            else -> binding.chip30.isChecked = true
        }
        binding.chipGroupInterval.setOnCheckedStateChangeListener { group, _ ->
            val checkedId = group.checkedChipId
            if (checkedId != View.NO_ID) {
                val chip = group.findViewById<com.google.android.material.chip.Chip>(checkedId)
                val value = chip.tag?.toString() ?: "30"
                prefs.edit().putString("refresh_interval", value).apply()
            }
        }

        // Clear cache
        binding.btnClearCache.setOnClickListener {
            requireContext().cacheDir.deleteRecursively()
            Toast.makeText(requireContext(), "缓存已清理", Toast.LENGTH_SHORT).show()
        }

        // Version
        try {
            val pInfo = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
            binding.tvVersion.text = pInfo.versionName
        } catch (e: Exception) {
            binding.tvVersion.text = "1.0.0"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
