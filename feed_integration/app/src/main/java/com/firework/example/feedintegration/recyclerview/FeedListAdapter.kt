@file:Suppress("DEPRECATION")

package com.firework.example.feedintegration.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.firework.error.FwErrorListener
import com.firework.example.feedintegration.R
import com.firework.videofeed.FeedItemClickListener
import com.firework.videofeed.FeedViewStateListener
import com.firework.videofeed.FwLifecycleAwareVideoFeedView
import com.firework.videofeed.FwVideoFeedView

class FeedListAdapter(
    private val values: List<ListItem>,
    private val lifecycle: Lifecycle,
    private val onFeedItemClickListener: FeedItemClickListener,
    private val onFeedViewStateListener: FeedViewStateListener,
    private val onFeedErrorListener: FwErrorListener,
) : RecyclerView.Adapter<FeedListAdapter.BaseViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.feed_list_item -> {
                FeedItemViewHolder(layoutInflater.inflate(R.layout.feed_list_item, parent, false))
            }
            R.layout.text_list_item -> {
                TextItemViewHolder(layoutInflater.inflate(R.layout.text_list_item, parent, false))
            }
            else -> error("Wrong view type: $viewType")
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
    ) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    override fun getItemViewType(position: Int): Int {
        return when (values[position]) {
            is FeedViewItem -> R.layout.feed_list_item
            is TextItem -> R.layout.text_list_item
            else -> R.layout.text_list_item
        }
    }

    abstract class BaseViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: ListItem)
    }

    inner class FeedItemViewHolder(view: View) : BaseViewHolder(view) {
        private var feedView: FwVideoFeedView? = null

        override fun bind(item: ListItem) {
            // This check is needed to avoid calling init() every time view goes in and out of the view port
            if (feedView == null) {
                val feedView: FwVideoFeedView =
                    view.findViewById<FwVideoFeedView?>(R.id.feedView).apply {
                        init((item as FeedViewItem).viewOptions)
                        setOnErrorListener(onFeedErrorListener)
                        setOnFeedItemClickListener(onFeedItemClickListener)
                        setOnFeedViewStateListener(onFeedViewStateListener)
                    }
                this.feedView = feedView
            }
        }
    }

    inner class TextItemViewHolder(view: View) : BaseViewHolder(view) {
        private val textView = view.findViewById<TextView>(R.id.text)

        override fun bind(item: ListItem) {
            textView.text = (item as TextItem).text
        }
    }
}
