package com.firework.example.feedresources.discovery

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.firework.common.feed.FeedResource
import com.firework.example.feedresources.R
import com.firework.example.feedresources.databinding.ActivityDiscoveryBinding
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.viewOptions

class DiscoveryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiscoveryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiscoveryBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setTitle(R.string.discovery_screen_title)

        setupDetails()

        initVideoFeedView()
    }

    @SuppressLint("SetTextI18n")
    private fun setupDetails() {
        binding.details.source.text = "Discovery"
        binding.details.channel.text = "N/A"
        binding.details.playlist.text = "N/A"
    }

    private fun initVideoFeedView() {
        val videoFeedView = binding.fwVideoFeedView

        val viewOptions =
            viewOptions {
                baseOptions {
                    // Check Channel, Playlist, or Dynamic screens for other feed sources
                    feedResource(FeedResource.Discovery)
                }
            }

        videoFeedView.init(viewOptions)
    }

    override fun onDestroy() {
        binding.fwVideoFeedView.destroy()
        super.onDestroy()
    }

    companion object {
        fun intent(context: Context) = Intent(context, DiscoveryActivity::class.java)
    }
}
