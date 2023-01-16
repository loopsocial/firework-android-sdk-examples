package tv.fw.sharelink

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tv.fw.common.feed.FeedResource
import tv.fw.videofeed.FwVideoFeedView
import tv.fw.videofeed.baseOptions
import tv.fw.videofeed.playerOptions
import tv.fw.videofeed.viewOptions

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
