package com.firework.example.feedresources.dynamiccontent

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.firework.common.feed.FeedResource
import com.firework.example.feedresources.BuildConfig.FW_CHANNEL_ID
import com.firework.example.feedresources.R
import com.firework.example.feedresources.databinding.ActivityDynamicContentBinding
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.viewOptions

class DynamicContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDynamicContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDynamicContentBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setTitle(R.string.dynamic_content_screen_title)

        setupDetails()

        initVideoFeedView()
    }

    @SuppressLint("SetTextI18n")
    private fun setupDetails() {
        binding.details.source.text = "Dynamic"
        binding.details.channel.text = FW_CHANNEL_ID
        binding.details.playlist.text = "N/A"
        binding.details.categories.text = "Test category"
    }

    private fun initVideoFeedView() {
        val videoFeedView = binding.fwVideoFeedView

        val categories = listOf("Test category")
        val parameters = mapOf("category" to categories)
        val feedResource = FeedResource.DynamicContent(channelId = FW_CHANNEL_ID, parameters = parameters)
        val viewOptions = viewOptions {
            baseOptions {
                feedResource(feedResource)
            }
        }
        videoFeedView.init(viewOptions)
    }

    companion object {
        fun intent(context: Context) = Intent(context, DynamicContentActivity::class.java)
    }
}
