package tv.fw.example.dynamic

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import tv.fw.common.feed.FeedResource
import tv.fw.example.dynamic.BuildConfig.FW_CHANNEL_ID
import tv.fw.example.dynamic.databinding.ActivityMainBinding
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
        binding.details.source.text = "Dynamic"
        binding.details.channel.text = FW_CHANNEL_ID
        binding.details.playlist.text = "N/A"
        binding.details.categories.text = "Test category"
    }

    private fun initVideoFeedView() {
        val videoFeedView = binding.videoFeedView

        val categories = listOf("Test category")
        val parameters = mapOf("category" to categories)
        val feedResource = FeedResource.DynamicContent(channelId = FW_CHANNEL_ID, parameters = parameters)

        val viewOptions = ViewOptions.Builder()
            // Check Discovery, Channel, or Playlist example apps for other feed sources
            .feedResource(feedResource)
            .build()

        videoFeedView.init(viewOptions)
    }
}
