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
import tv.fw.common.PlayerMode
import tv.fw.common.ad.AdBadgeTextType
import tv.fw.common.cta.CtaDelay
import tv.fw.common.cta.CtaDelayUnit
import tv.fw.common.feed.FeedLayout
import tv.fw.common.feed.FeedResource
import tv.fw.common.feed.FeedTitlePosition
import tv.fw.example.viewoptions.databinding.ActivityMainBinding
import tv.fw.videofeed.VideoFeedView
import tv.fw.videofeed.options.ViewOptions

@Suppress("MagicNumber")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val videoFeedView: VideoFeedView
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
        val viewOptions = ViewOptions.Builder()
            .adBadgeBackColor(ContextCompat.getColor(this, R.color.cyan))
            .adBadgeIsHidden(false)
            .adBadgeLabel(AdBadgeTextType.SPONSORED)
            .adBadgeShowOnThumbnails(true)
            .adBadgeTextColor(ContextCompat.getColor(this, R.color.white))
            .adBadgeTypeface(ResourcesCompat.getFont(this, R.font.roboto_black)!!)
            .autoPlayOnComplete(true)
            .backgroundColor(ContextCompat.getColor(this, R.color.black))
            .columnCount(3)
            .ctaButtonBackgroundColor(ContextCompat.getColor(this, R.color.cyan))
            .ctaButtonDelay(CtaDelay(0.2f, CtaDelayUnit.PERCENTAGE))
            .ctaButtonTextColor(ContextCompat.getColor(this, R.color.white))
            .ctaButtonTypeface(ResourcesCompat.getFont(this, R.font.roboto_black)!!)
            .feedLayout(FeedLayout.GRID)
            .feedResource(FeedResource.Discovery)
            .feedTitleBackgroundColor(ContextCompat.getColor(this, R.color.purple_transparent))
            .feedTitlePosition(FeedTitlePosition.NESTED)
            .feedTitleTextColor(ContextCompat.getColor(this, R.color.black))
            .feedTitleTextNumberOfLines(1)
            .feedTitleTextPadding(dpToPx(8f))
            .feedTitleTextSize(spToPx(14f))
            .feedTitleTextTypeface(ResourcesCompat.getFont(this, R.font.roboto_regular)!!)
            .itemSpacing(dpToPx(4f))
            .muteOnLaunch(true)
            .playIconWidth(dpToPx(24f))
            .playerMode(PlayerMode.FIT_MODE)
            .roundedCorner(true)
            .roundedCornerRadius(dpToPx(16f))
            .showFeedTitle(true)
            .showFireworkLogo(true)
            .showMuteButton(true)
            .showPlayIcon(true)
            .showPlayPauseButton(true)
            .showShareButton(true)
            .supportBackwardAds(true)
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
