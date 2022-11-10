package tv.fw.example.discovery

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import tv.fw.common.feed.FeedResource
import tv.fw.example.discovery.databinding.ActivityMainBinding
import tv.fw.videofeed.options.ViewOptions

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        setupVideoFeedView()
    }

    private fun setupVideoFeedView() {
        val videoFeedView = binding.videoFeedView

        val viewOptions = ViewOptions.Builder()
            .feedResource(FeedResource.Discovery) // Check Channel or Playlist Example apps for other feed sources
            .build()

        videoFeedView.init(viewOptions)
    }
}
