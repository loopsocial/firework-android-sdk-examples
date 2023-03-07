package com.firework.example.feedresources.playlist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.firework.common.feed.FeedResource
import com.firework.example.feedresources.BuildConfig.FW_CHANNEL_ID
import com.firework.example.feedresources.BuildConfig.FW_PLAYLIST_ID
import com.firework.example.feedresources.R
import com.firework.example.feedresources.databinding.ActivityPlaylistBinding
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.viewOptions

class PlaylistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaylistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaylistBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setTitle(R.string.playlist_screen_title)

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
        val videoFeedView = binding.fwVideoFeedView

        val viewOptions = viewOptions {
            baseOptions {
                feedResource(FeedResource.Playlist(channelId = FW_CHANNEL_ID, playlistId = FW_PLAYLIST_ID))
            }
        }

        videoFeedView.init(viewOptions)
    }

    override fun onDestroy() {
        binding.fwVideoFeedView.destroy()
        super.onDestroy()
    }

    companion object {
        fun intent(context: Context) = Intent(context, PlaylistActivity::class.java)
    }
}
