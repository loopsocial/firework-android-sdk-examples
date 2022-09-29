package tv.fw.example.discovery

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import tv.fw.carousel.FeedItemClickListener
import tv.fw.carousel.options.ViewOptions
import tv.fw.common.PlayerMode
import tv.fw.common.ad.AdBadgeTextType
import tv.fw.common.feed.FeedLayout
import tv.fw.common.feed.FeedResource
import tv.fw.example.discovery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        setupCarouselView()
    }

    private fun setupCarouselView() {
        val carouselView = binding.carouselView

        // You can create a view options with your custom parameters or ignore this step if you already have them in your layout
        // Also creating ViewOptions is not mandatory and you can let the widget use its default values
        val viewOptions = ViewOptions.Builder()
            .adBadgeBackColor(Color.CYAN)
            .adBadgeLabel(AdBadgeTextType.SPONSORED)
            .adBadgeTextColor(Color.WHITE)
            .autoPlayOnComplete(true)
            .backgroundColor(Color.BLACK)
            .columnCount(3)
            .feedBackgroundColor(Color.GRAY)
            .feedLayout(FeedLayout.GRID)
            .feedResource(FeedResource.Discovery) // It also can be Channel and Playlist
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

        // Uncomment this line if you prefer setting the Carousel view options in code instead of XML layout
        // carouselView.init(viewOptions)

        carouselView.init()

        carouselView.setOnFeedItemClickListener(object : FeedItemClickListener {
            override fun onItemClicked(feedItem: FeedItemClickListener.FeedItem) {
                // maybe trigger some analytics event
            }
        })
    }
}
