package tv.fw.example.channel

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import tv.fw.common.feed.FeedResource
import tv.fw.example.channel.BuildConfig.FW_CHANNEL_ID
import tv.fw.example.channel.databinding.ActivityMainBinding
import tv.fw.videofeed.options.ViewOptions

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

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

        val viewOptions = ViewOptions.Builder()
            // Check Discovery or Playlist Example apps for other feed sources
            .feedResource(FeedResource.Channel(channelId = FW_CHANNEL_ID))
            .build()

        videoFeedView.init(viewOptions)
    }
}
