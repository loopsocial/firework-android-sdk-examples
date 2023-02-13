package tv.fw.sharelink

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firework.common.feed.FeedResource
import com.firework.videofeed.FwVideoFeedView
import com.firework.videofeed.baseOptions
import com.firework.videofeed.playerOptions
import com.firework.videofeed.viewOptions

class MainActivity : AppCompatActivity() {
    private lateinit var videoFeedView: FwVideoFeedView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val videoFeedView = findViewById<FwVideoFeedView>(R.id.fwVideoFeedView)
        val viewOptions = viewOptions {
            baseOptions {
                feedResource(FeedResource.Discovery)
            }
            playerOptions {
                showShareButton(true)
            }
        }
        videoFeedView.init(viewOptions)
    }

    override fun onDestroy() {
        videoFeedView.destroy()
        super.onDestroy()
    }
}
