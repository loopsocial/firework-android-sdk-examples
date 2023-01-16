package tv.fw.example.channel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import tv.fw.common.feed.FeedResource
import tv.fw.example.feedResources.BuildConfig.FW_CHANNEL_ID
import tv.fw.example.feedResources.R
import tv.fw.example.feedResources.databinding.ActivityChannelBinding
import tv.fw.videofeed.baseOptions
import tv.fw.videofeed.viewOptions

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
        val videoFeedView = binding.videoFeedView

        val viewOptions = viewOptions {
            baseOptions {
                feedResource(FeedResource.Channel(channelId = FW_CHANNEL_ID))
            }
        }

        videoFeedView.init(viewOptions)
    }

    companion object {
        fun intent(context: Context) = Intent(context, ChannelActivity::class.java)
    }
}
