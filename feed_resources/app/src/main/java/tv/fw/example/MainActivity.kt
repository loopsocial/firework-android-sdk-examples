package tv.fw.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tv.fw.example.channel.ChannelActivity
import tv.fw.example.discovery.DiscoveryActivity
import tv.fw.example.dynamiccontent.DynamicContentActivity
import tv.fw.example.feedResources.databinding.ActivityMainBinding
import tv.fw.example.playlist.PlaylistActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnDiscovery.setOnClickListener {
            startActivity(DiscoveryActivity.intent(this))
        }
        binding.btnPlaylist.setOnClickListener {
            startActivity(PlaylistActivity.intent(this))
        }
        binding.btnChannel.setOnClickListener {
            startActivity(ChannelActivity.intent(this))
        }
        binding.btnDynamicContent.setOnClickListener {
            startActivity(DynamicContentActivity.intent(this))
        }
    }
}
