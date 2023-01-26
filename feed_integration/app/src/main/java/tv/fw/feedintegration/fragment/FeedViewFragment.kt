package tv.fw.feedintegration.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tv.fw.common.feed.FeedResource
import tv.fw.feedintegration.R
import tv.fw.videofeed.FwVideoFeedView
import tv.fw.videofeed.baseOptions
import tv.fw.videofeed.viewOptions

class FeedViewFragment : Fragment() {
    private lateinit var videoFeedView: FwVideoFeedView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_feed_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoFeedView = view.findViewById(R.id.videoFeedView)
        val viewOptions = viewOptions {
            baseOptions {
                feedResource(FeedResource.Discovery)
            }
        }
        videoFeedView.init(viewOptions)
    }

    override fun onDestroyView() {
        videoFeedView.destroy()
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = FeedViewFragment()
    }
}
