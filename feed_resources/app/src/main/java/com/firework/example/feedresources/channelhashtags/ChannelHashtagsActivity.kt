package com.firework.example.feedresources.channelhashtags

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.firework.common.feed.FeedResource
import com.firework.example.feedresources.BuildConfig.FW_CHANNEL_ID
import com.firework.example.feedresources.R
import com.firework.example.feedresources.databinding.ActivityChannelHashtagsBinding
import com.firework.videofeed.FwVideoFeedView
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.viewOptions

class ChannelHashtagsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChannelHashtagsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChannelHashtagsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setTitle(R.string.channel_hashtags_screen_title)

        setupDetails()

        initVideoFeedView()

        binding.etHashtagFilter.setText(HASHTAG_FILTER_EXPRESSION)
        binding.btnApplyFilter.setOnClickListener {
            updateViewOptions(binding.etHashtagFilter.text.toString())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupDetails() {
        binding.details.source.text = "Channel"
        binding.details.channel.text = FW_CHANNEL_ID
        binding.details.playlist.text = "N/A"
    }

    private fun initVideoFeedView() {
        val videoFeedView = binding.fwVideoFeedView

        val viewOptions = viewOptions {
            baseOptions {
                feedResource(FeedResource.ChannelHashtag(channelId = FW_CHANNEL_ID, HASHTAG_FILTER_EXPRESSION))
            }
        }

        videoFeedView.init(viewOptions)
    }

    private fun updateViewOptions(hashTagFilter: String) {
        binding.fwVideoFeedView.destroy()
        binding.feedContainer.removeAllViews()
        val videoFeedView = FwVideoFeedView(this)
        val viewOptions = viewOptions {
            baseOptions {
                feedResource(FeedResource.ChannelHashtag(channelId = FW_CHANNEL_ID, hashTagFilter))
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
        /**
         * Hashtag filter expression is an s-expression used to provide feeds filtered by hashtags with specified criteria.
         * Queries are specified with boolean predicates on what hashtags are there on the video.
         * For instance, (and sport food) (or sport food) (and sport (or food comedy)) sport are all valid expressions.
         * Non-UTF-8 characters are not allowed.
         * If using boolean predicates, the expression needs to be wrapped with parenthesis.
         */
        private const val HASHTAG_FILTER_EXPRESSION = "(or food art cats pets beauty fashion travel)"

        fun intent(context: Context) = Intent(context, ChannelHashtagsActivity::class.java)
    }
}
