package tv.fw.example.discovery

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import tv.fw.carousel.options.ViewOptions
import tv.fw.common.AdBadgeTextType
import tv.fw.common.FeedLayout
import tv.fw.common.FeedResource
import tv.fw.common.PlayerMode
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
            .feedLayout(FeedLayout.GRID)
            .columnCount(3)
            .pageSize(30)
            .backgroundColor(Color.BLACK)
            .itemSpacing(48)
            .roundedCorner(true)
            .roundedCornerRadius(2.0F)
            .showFeedTitle(true)
            .feedTextColor(Color.WHITE)
            .feedBackgroundColor(Color.GRAY)
            .feedTextNumberOfLines(1)
            .feedTextPadding(12)
            .feedTextSize(12)
            .playerMode(PlayerMode.FIT_MODE)
            .showPlayIcon(true)
            .supportBackwardAds(true)
            .feedResource(FeedResource.Discovery) // It also can be Channel and Playlist
            .adBadgeBackColor(Color.CYAN)
            .adBadgeTextColor(Color.WHITE)
            .adBadgeLabel(AdBadgeTextType.SPONSORED)
            .build()

        // carouselView.init(viewOptions)

        carouselView.init()
    }
}