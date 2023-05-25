package com.firework.example.feedresources

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firework.example.feedresources.channel.ChannelActivity
import com.firework.example.feedresources.channelhashtags.ChannelHashtagsActivity
import com.firework.example.feedresources.databinding.ActivityMainBinding
import com.firework.example.feedresources.discovery.DiscoveryActivity
import com.firework.example.feedresources.dynamiccontent.DynamicContentActivity
import com.firework.example.feedresources.playlist.PlaylistActivity
import com.firework.example.feedresources.sku.SkuActivity

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
        binding.channelHashtags.setOnClickListener {
            startActivity(ChannelHashtagsActivity.intent(this))
        }
        binding.sku.setOnClickListener {
            startActivity(SkuActivity.intent(this))
        }
    }
}
