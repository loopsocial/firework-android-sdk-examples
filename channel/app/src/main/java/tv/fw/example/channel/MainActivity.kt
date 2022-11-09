package tv.fw.example.channel

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import tv.fw.common.feed.FeedResource
import tv.fw.example.channel.databinding.ActivityMainBinding
import tv.fw.videofeed.options.ViewOptions

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initVideoFeedView()
    }

    private fun initVideoFeedView() {
        val videoFeedView = binding.videoFeedView

        val viewOptions = ViewOptions.Builder()
            .feedResource(FeedResource.Channel(BuildConfig.FW_CHANNEL_ID)) // Check Discovery or Playlist Example apps for other feed sources
            .build()

        videoFeedView.init(viewOptions)
    }
}
