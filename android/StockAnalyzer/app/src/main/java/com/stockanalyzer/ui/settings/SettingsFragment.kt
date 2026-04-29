package com.stockanalyzer.ui.settings

import android.os.Bundle
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.stockanalyzer.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        findPreference<Preference>("clear_cache")?.setOnPreferenceClickListener {
            requireContext().cacheDir.deleteRecursively()
            Toast.makeText(requireContext(), "缓存已清理", Toast.LENGTH_SHORT).show()
            true
        }
    }
}
