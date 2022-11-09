package tv.fw.example.playlist

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import tv.fw.common.feed.FeedResource
import tv.fw.example.playlist.BuildConfig.FW_CHANNEL_ID
import tv.fw.example.playlist.BuildConfig.FW_PLAYLIST_ID
import tv.fw.example.playlist.databinding.ActivityMainBinding
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

        val playlistFeedResource = FeedResource.Playlist(FW_CHANNEL_ID, FW_PLAYLIST_ID)

        val viewOptions = ViewOptions.Builder()
            .feedResource(playlistFeedResource) // Check Discovery or Playlist Example apps for other feed sources
            .build()

        videoFeedView.init(viewOptions)
    }
}
