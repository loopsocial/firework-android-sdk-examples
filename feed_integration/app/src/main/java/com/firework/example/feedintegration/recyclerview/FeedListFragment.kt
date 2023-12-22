package com.firework.example.feedintegration.recyclerview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firework.common.feed.FeedResource
import com.firework.error.FwError
import com.firework.error.FwErrorListener
import com.firework.example.feedintegration.BuildConfig.FW_CHANNEL_ID
import com.firework.example.feedintegration.BuildConfig.FW_PLAYLIST_ID
import com.firework.example.feedintegration.R
import com.firework.videofeed.FeedItemClickListener
import com.firework.videofeed.FeedViewState
import com.firework.videofeed.FeedViewStateListener
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.viewOptions
import java.util.UUID

class FeedListFragment : Fragment(), FeedItemClickListener, FwErrorListener, FeedViewStateListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_feed_list, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = FeedListAdapter(getListItems(), lifecycle, this, this, this)
    }

    private fun getListItems(): List<ListItem> {
        val playlistFeedViewOptions =
            viewOptions {
                baseOptions {
                    feedResource(FeedResource.Playlist(FW_CHANNEL_ID, FW_PLAYLIST_ID))
                }
            }
        return listOf(
            TextItem(LOREN_IPSUM),
            TextItem(LOREN_IPSUM),
            FeedViewItem(UUID.randomUUID().toString(), playlistFeedViewOptions),
            TextItem(LOREN_IPSUM),
            TextItem(LOREN_IPSUM),
            TextItem(LOREN_IPSUM),
        )
    }

    override fun onItemClicked(feedItem: FeedItemClickListener.FeedItem) {
        Log.d(TAG, "onFeedItemClicked: $feedItem")
    }

    override fun onLoadStateChanged(feedViewState: FeedViewState) {
        Log.d(TAG, "Feed feedViewState: $feedViewState")
    }

    override fun onFwError(error: FwError) {
        Log.d(TAG, "onFwError: $error")
    }

    companion object {
        fun newInstance() = FeedListFragment()

        private val TAG = FeedListFragment::class.simpleName
        private const val LOREN_IPSUM = """ Lorem Ipsum is simply dummy text of the printing and typesetting industry.
            Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
            when an unknown printer took a galley of type and scrambled it to make a type specimen book.
            It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.
            It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop
            publishing software like Aldus PageMaker including versions of Lorem Ipsum.
              """
    }
}
