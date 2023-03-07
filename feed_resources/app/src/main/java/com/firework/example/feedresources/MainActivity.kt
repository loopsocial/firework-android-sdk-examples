package com.firework.example.feedresources

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firework.example.feedresources.channel.ChannelActivity
import com.firework.example.feedresources.databinding.ActivityMainBinding
import com.firework.example.feedresources.discovery.DiscoveryActivity
import com.firework.example.feedresources.dynamiccontent.DynamicContentActivity
import com.firework.example.feedresources.playlist.PlaylistActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.discovery.setOnClickListener {
            startActivity(DiscoveryActivity.intent(this))
        }
        binding.playlist.setOnClickListener {
            startActivity(PlaylistActivity.intent(this))
        }
        binding.channel.setOnClickListener {
            startActivity(ChannelActivity.intent(this))
        }
        binding.dynamicContent.setOnClickListener {
            startActivity(DynamicContentActivity.intent(this))
        }
    }
}