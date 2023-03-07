package com.firework.example.feedintegration.addremove

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.firework.common.feed.FeedResource
import com.firework.example.feedintegration.R
import com.firework.example.feedintegration.databinding.ActivityAddRemoveIntegrationBinding
import com.firework.videofeed.FwVideoFeedView
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.viewOptions

class AddRemoveIntegrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRemoveIntegrationBinding

    private var videoFeedView: FwVideoFeedView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRemoveIntegrationBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setTitle(R.string.add_remove_screen_title)
        supportActionBar?.title = getString(R.string.add_remove_screen_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        initAddRemoveButtons()
    }

    private fun initAddRemoveButtons() {
        binding.addFeed.setOnClickListener {
            addFeed()
            updateButtonsAvailability()
        }

        binding.removeFeed.setOnClickListener {
            removeFeed()
            updateButtonsAvailability()
        }

        updateButtonsAvailability()
    }

    private fun addFeed() {
        val videoFeedView = FwVideoFeedView(this)

        val viewOptions = viewOptions {
            baseOptions {
                feedResource(FeedResource.Discovery)
            }
        }

        videoFeedView.init(viewOptions)

        binding.feedContainer.addView(videoFeedView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)

        this.videoFeedView = videoFeedView
    }

    private fun removeFeed() {
        val videoFeedView = requireNotNull(this.videoFeedView)

        binding.feedContainer.removeView(videoFeedView)

        videoFeedView.destroy()

        this.videoFeedView = null
    }

    private fun updateButtonsAvailability() {
        val isFeedAdded = this.videoFeedView != null

        binding.addFeed.isEnabled = !isFeedAdded
        binding.removeFeed.isEnabled = isFeedAdded
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        videoFeedView?.destroy()
        super.onDestroy()
    }

    companion object {
        fun intent(context: Context) = Intent(context, AddRemoveIntegrationActivity::class.java)
    }
}
