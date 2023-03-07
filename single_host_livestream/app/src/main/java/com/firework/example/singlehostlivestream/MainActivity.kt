package com.firework.example.singlehostlivestream

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.firework.common.feed.FeedResource
import com.firework.example.singlehostlivestream.BuildConfig.FW_CHANNEL_ID
import com.firework.example.singlehostlivestream.BuildConfig.FW_PLAYLIST_ID
import com.firework.example.singlehostlivestream.databinding.ActivityMainBinding
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.viewOptions

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

        // Replace this playlist with one including SingleHost Livestream
        val playlistFeedResource = FeedResource.Playlist(FW_CHANNEL_ID, FW_PLAYLIST_ID)

        val viewOptions = viewOptions {
            baseOptions {
                feedResource(playlistFeedResource)
            }
        }

        videoFeedView.init(viewOptions)
    }
}
