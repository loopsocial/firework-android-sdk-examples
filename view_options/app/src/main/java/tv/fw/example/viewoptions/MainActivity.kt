package tv.fw.example.viewoptions

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.firework.common.PlayerMode
import com.firework.common.ad.AdBadgeOption
import com.firework.common.ad.AdBadgeTextType
import com.firework.common.ad.AdOption
import com.firework.common.cta.CtaDelay
import com.firework.common.cta.CtaDelayUnit
import com.firework.common.feed.FeedLayout
import com.firework.common.feed.FeedResource
import com.firework.common.feed.FeedTitlePosition
import com.firework.videofeed.FwVideoFeedView
import com.firework.videofeed.options.BaseOption
import com.firework.videofeed.options.CtaOption
import com.firework.videofeed.options.LayoutOption
import com.firework.videofeed.options.PlayerOption
import com.firework.videofeed.options.TitleOption
import com.firework.videofeed.options.ViewOptions
import tv.fw.example.viewoptions.databinding.ActivityMainBinding

@Suppress("MagicNumber")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val videoFeedView: FwVideoFeedView
        get() = binding.videoFeedView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        setupDetails()

        initVideoFeedView()

        setupVideoFeedViewCallbacks()
    }

    @SuppressLint("SetTextI18n")
    private fun setupDetails() {
        binding.details.source.text = "Discovery"
        binding.details.channel.text = "N/A"
        binding.details.playlist.text = "N/A"
    }

    private fun initVideoFeedView() {

        /**
         * Initialization method 1:
         * - Using [VideoFeedView]'s attributes in the XML layout file
         */
        // videoFeedView.init()

        /**
         * Initialization method 2:
         * - Using [ViewOptions.Builder] at runtime
         */
        val adBadgeOption = AdBadgeOption.Builder()
            .adBadgeBackColor(ContextCompat.getColor(this, R.color.cyan))
            .adBadgeIsHidden(false)
            .adBadgeLabel(AdBadgeTextType.SPONSORED)
            .adBadgeShowOnThumbnails(true)
            .adBadgeTextColor(ContextCompat.getColor(this, R.color.white))
            .adBadgeTypeface(ResourcesCompat.getFont(this, R.font.roboto_black)!!)
            .build()

        val ctaOption = CtaOption.Builder()
            .ctaDelay(CtaDelay(0.2f, CtaDelayUnit.PERCENTAGE))
            .build()

        val titleOption = TitleOption.Builder()
            .feedTitleBackgroundColor(ContextCompat.getColor(this, R.color.purple_transparent))
            .feedTitleTextColor(ContextCompat.getColor(this, R.color.black))
            .feedTitleTextNumberOfLines(1)
            .feedTitleTextPadding(dpToPx(8f))
            .feedTitleTextSize(spToPx(14f))
            .feedTitleTextTypeface(ResourcesCompat.getFont(this, R.font.roboto_regular)!!)
            .showFeedTitle(true)
            .build()

        val layoutOption = LayoutOption.Builder()
            .feedLayout(FeedLayout.GRID)
            .feedTitlePosition(FeedTitlePosition.NESTED)
            .itemSpacing(dpToPx(4f))
            .backgroundColor(ContextCompat.getColor(this, R.color.black))
            .columnCount(3)
            .playIconWidth(dpToPx(24f))
            .roundedCorner(true)
            .roundedCornerRadius(dpToPx(16f))
            .showPlayIcon(true)
            .build()

        val playerOption = PlayerOption.Builder()
            .autoPlayOnComplete(true)
            .autoplay(true)
            .playerMode(PlayerMode.FIT_MODE)
            .showFireworkLogo(true)
            .showMuteButton(true)
            .showPlayPauseButton(true)
            .showShareButton(true)
            .build()

        val adOption = AdOption.Builder()
            .supportBackwardAds(true)
            .build()

        val baseOption = BaseOption.Builder()
            .feedResource(FeedResource.Discovery)
            .build()

        val viewOptions = ViewOptions.Builder()
            .adBadgeOption(adBadgeOption)
            .ctaOption(ctaOption)
            .titleOption(titleOption)
            .layoutOption(layoutOption)
            .playerOption(playerOption)
            .adOption(adOption)
            .baseOption(baseOption)
            .build()

        videoFeedView.init(viewOptions)
    }

    private fun setupVideoFeedViewCallbacks() {

        // All errors related to this video feed view can be collected using this callback to handle
        videoFeedView.setOnErrorListener { fwError ->
            Log.e("FireworkSDK", "Firework VideoFeedView error: $fwError")
        }

        // All feed item clicks related to this video feed view can be collected using this callback
        // to maybe trigger some analytics event
        videoFeedView.setOnFeedItemClickListener { feedItem ->
            Log.e("FireworkSDK", "On feed item clicked: $feedItem")
        }
    }

    companion object {
        private fun Context.spToPx(sp: Float): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics).toInt()
        }

        private fun Context.dpToPx(dp: Float): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
        }
    }
}
