package com.stockanalyzer.ui.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.stockanalyzer.databinding.ActivityNewsDetailBinding

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.toolbar.setNavigationOnClickListener { finish() }

        val title   = intent.getStringExtra(EXTRA_TITLE)   ?: ""
        val source  = intent.getStringExtra(EXTRA_SOURCE)  ?: ""
        val time    = intent.getStringExtra(EXTRA_TIME)    ?: ""
        val content = intent.getStringExtra(EXTRA_CONTENT) ?: ""
        val url     = intent.getStringExtra(EXTRA_URL)     ?: ""

        binding.tvTitle.text   = title
        binding.tvSource.text  = source
        binding.tvTime.text    = time
        binding.tvContent.text = content.ifEmpty { "暂无详细内容" }

        if (url.isNotEmpty()) {
            binding.btnOpenUrl.visibility = View.VISIBLE
            binding.btnOpenUrl.setOnClickListener {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                } catch (_: Exception) {}
            }
        }
    }

    companion object {
        const val EXTRA_TITLE   = "title"
        const val EXTRA_SOURCE  = "source"
        const val EXTRA_TIME    = "time"
        const val EXTRA_CONTENT = "content"
        const val EXTRA_URL     = "url"
    }
}
