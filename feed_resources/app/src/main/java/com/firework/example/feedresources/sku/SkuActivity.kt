package com.firework.example.feedresources.sku

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.firework.common.feed.FeedResource
import com.firework.example.feedresources.BuildConfig.FW_CHANNEL_ID
import com.firework.example.feedresources.R
import com.firework.example.feedresources.databinding.ActivitySkuBinding
import com.firework.videofeed.FwVideoFeedView
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.viewOptions

class SkuActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySkuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySkuBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setTitle(R.string.sku_screen_title)

        setupDetails()

        initVideoFeedView()

        binding.etSkuIds.setText(DEFAULT_IDS.joinToString(","))
        binding.btnApplyFilter.setOnClickListener {
            val productId = binding.etSkuIds.text.toString().split(",")
            updateViewOptions(productId)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupDetails() {
        binding.details.source.text = "Sku"
        binding.details.channel.text = FW_CHANNEL_ID
        binding.details.playlist.text = "N/A"
    }

    private fun initVideoFeedView() {
        val videoFeedView = binding.fwVideoFeedView

        val viewOptions = viewOptions {
            baseOptions {
                feedResource(FeedResource.Sku(channelId = FW_CHANNEL_ID, DEFAULT_IDS))
            }
        }

        videoFeedView.init(viewOptions)
    }

    private fun updateViewOptions(skuIds: List<String>) {
        binding.fwVideoFeedView.destroy()
        binding.feedContainer.removeAllViews()
        val videoFeedView = FwVideoFeedView(this)
        val viewOptions = viewOptions {
            baseOptions {
                feedResource(FeedResource.Sku(channelId = FW_CHANNEL_ID, productIds = skuIds))
            }
        }
        binding.feedContainer.addView(videoFeedView)
        videoFeedView.init(viewOptions)
    }

    override fun onDestroy() {
        binding.fwVideoFeedView.destroy()
        super.onDestroy()
    }

    companion object {
        private val DEFAULT_IDS = listOf("6009775390877", "6009775423645", "6009775685789")

        fun intent(context: Context) = Intent(context, SkuActivity::class.java)
    }
}
