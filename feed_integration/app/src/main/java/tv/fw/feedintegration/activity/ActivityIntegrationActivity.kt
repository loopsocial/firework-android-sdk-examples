package tv.fw.feedintegration.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import tv.fw.common.feed.FeedResource
import tv.fw.feedintegration.R
import tv.fw.feedintegration.databinding.ActivityActivityIntegrationBinding
import tv.fw.videofeed.baseOptions
import tv.fw.videofeed.viewOptions

class ActivityIntegrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityActivityIntegrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivityIntegrationBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setTitle(R.string.activity_screen_title)
        supportActionBar?.title = getString(R.string.activity_screen_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        initVideoFeedView()
    }

    private fun initVideoFeedView() {
        val videoFeedView = binding.fwVideoFeedView

        val viewOptions = viewOptions {
            baseOptions {
                feedResource(FeedResource.Discovery)
            }
        }

        videoFeedView.init(viewOptions)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        binding.fwVideoFeedView.destroy()
        super.onDestroy()
    }

    companion object {
        fun intent(context: Context) = Intent(context, ActivityIntegrationActivity::class.java)
    }
}
