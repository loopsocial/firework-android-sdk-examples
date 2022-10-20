package tv.fw.example.discovery

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import tv.fw.common.PlayerMode
import tv.fw.common.ad.AdBadgeTextType
import tv.fw.common.feed.FeedLayout
import tv.fw.common.feed.FeedResource
import tv.fw.example.discovery.databinding.ActivityMainBinding
import tv.fw.videofeed.FeedItemClickListener
import tv.fw.videofeed.options.ViewOptions

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        setupVideoFeedView()
    }

    private fun setupVideoFeedView() {
        val videoFeedView = binding.videoFeedView

        // You ignore this step if you already have the options in your layout,
        // Or create a ViewOptions object with your custom parameters.
        // Creating ViewOptions or passing all options is not mandatory and the widget use its default values
        val viewOptions = ViewOptions.Builder()
            .adBadgeBackColor(Color.CYAN)
            .adBadgeLabel(AdBadgeTextType.SPONSORED)
            .adBadgeTextColor(Color.WHITE)
            .autoPlayOnComplete(true)
            .backgroundColor(Color.BLACK)
            .columnCount(3)
            .feedBackgroundColor(Color.GRAY)
            .feedLayout(FeedLayout.GRID)
            .feedResource(FeedResource.Discovery) // Check Channel or Playlist Example apps for other feed sources
            .feedTextColor(Color.WHITE)
            .feedTextNumberOfLines(1)
            .feedTextPadding(12)
            .feedTextSize(12)
            .itemSpacing(48)
            .muteOnLaunch(true)
            .playerMode(PlayerMode.FIT_MODE)
            .roundedCorner(true)
            .roundedCornerRadius(2.0F)
            .showFeedTitle(true)
            .showFireworkLogo(true)
            .showPlayIcon(true)
            .showPlayPauseButton(true)
            .showShareButton(true)
            .supportBackwardAds(true)
            .build()

        // Uncomment this line if you prefer setting the videoFeedView options in code instead of XML layout
        // videoFeedView.init(viewOptions)

        videoFeedView.init(viewOptions)

        videoFeedView.setOnFeedItemClickListener(object : FeedItemClickListener {
            override fun onItemClicked(feedItem: FeedItemClickListener.FeedItem) {
                // maybe trigger some analytics event
            }
        })
    }
}
