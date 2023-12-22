package com.firework.example.feedresources.channel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.firework.common.feed.FeedResource
import com.firework.example.feedresources.BuildConfig.FW_CHANNEL_ID
import com.firework.example.feedresources.R
import com.firework.example.feedresources.databinding.ActivityChannelBinding
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.viewOptions

class ChannelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChannelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChannelBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setTitle(R.string.channel_screen_title)

        setupDetails()

        initVideoFeedView()
    }

    @SuppressLint("SetTextI18n")
    private fun setupDetails() {
        binding.details.source.text = "Channel"
        binding.details.channel.text = FW_CHANNEL_ID
        binding.details.playlist.text = "N/A"
    }

    private fun initVideoFeedView() {
        val videoFeedView = binding.fwVideoFeedView

        val viewOptions =
            viewOptions {
                baseOptions {
                    feedResource(FeedResource.Channel(channelId = FW_CHANNEL_ID))
                }
            }

        videoFeedView.init(viewOptions)
    }

    override fun onDestroy() {
        binding.fwVideoFeedView.destroy()
        super.onDestroy()
    }

    companion object {
        fun intent(context: Context) = Intent(context, ChannelActivity::class.java)
    }
}
