package tv.fw.example.multihostlivestream

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.firework.common.feed.FeedResource
import com.firework.videofeed.baseOptions
import com.firework.videofeed.viewOptions
import tv.fw.example.multihostlivestream.BuildConfig.FW_CHANNEL_ID
import tv.fw.example.multihostlivestream.BuildConfig.FW_PLAYLIST_ID
import tv.fw.example.multihostlivestream.databinding.ActivityMainBinding

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
        binding.details.source.text = "Playlist"
        binding.details.channel.text = FW_CHANNEL_ID
        binding.details.playlist.text = FW_PLAYLIST_ID
    }

    private fun initVideoFeedView() {
        val videoFeedView = binding.videoFeedView

        val playlistFeedResource = FeedResource.Playlist(FW_CHANNEL_ID, FW_PLAYLIST_ID)

        val viewOptions = viewOptions {
            baseOptions {
                feedResource(playlistFeedResource)
            }
        }

        videoFeedView.init(viewOptions)
    }
}
