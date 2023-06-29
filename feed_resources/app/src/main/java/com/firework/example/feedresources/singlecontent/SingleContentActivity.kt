package com.firework.example.feedresources.singlecontent

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.firework.common.feed.FeedResource
import com.firework.example.feedresources.BuildConfig.FW_CONTENT_ID
import com.firework.example.feedresources.R
import com.firework.example.feedresources.databinding.ActivitySingleContentBinding
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.viewOptions

class SingleContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleContentBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setTitle(R.string.single_content_screen_title)

        setupDetails()

        initVideoFeedView()
    }

    @SuppressLint("SetTextI18n")
    private fun setupDetails() {
        binding.details.source.text = "SingleContent"
        binding.details.elementId.text = FW_CONTENT_ID
    }

    private fun initVideoFeedView() {
        val videoFeedView = binding.fwVideoFeedView

        val viewOptions = viewOptions {
            baseOptions {
                // Check Channel, Playlist, or Dynamic screens for other feed sources
                feedResource(FeedResource.SingleContent(FW_CONTENT_ID))
            }
        }

        videoFeedView.init(viewOptions)
    }

    override fun onDestroy() {
        binding.fwVideoFeedView.destroy()
        super.onDestroy()
    }

    companion object {
        fun intent(context: Context) = Intent(context, SingleContentActivity::class.java)
    }
}
