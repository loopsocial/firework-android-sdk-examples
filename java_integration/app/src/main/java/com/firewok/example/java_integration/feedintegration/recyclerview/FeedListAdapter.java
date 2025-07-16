package com.firewok.example.java_integration.feedintegration.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import com.firework.error.FwErrorListener;
import com.firewok.example.java_integration.R;
import com.firework.videofeed.FeedItemClickListener;
import com.firework.videofeed.FeedViewStateListener;
import com.firework.videofeed.FwVideoFeedView;
import java.util.List;

public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.BaseViewHolder> {
    private final List<ListItem> values;
    private final Lifecycle lifecycle;
    private final FeedItemClickListener onFeedItemClickListener;
    private final FeedViewStateListener onFeedViewStateListener;
    private final FwErrorListener onFeedErrorListener;

    public FeedListAdapter(List<ListItem> values,
                          Lifecycle lifecycle,
                          FeedItemClickListener onFeedItemClickListener,
                          FeedViewStateListener onFeedViewStateListener,
                          FwErrorListener onFeedErrorListener) {
        this.values = values;
        this.lifecycle = lifecycle;
        this.onFeedItemClickListener = onFeedItemClickListener;
        this.onFeedViewStateListener = onFeedViewStateListener;
        this.onFeedErrorListener = onFeedErrorListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.feed_list_item) {
            return new FeedItemViewHolder(layoutInflater.inflate(R.layout.feed_list_item, parent, false));
        } else if (viewType == R.layout.text_list_item) {
            return new TextItemViewHolder(layoutInflater.inflate(R.layout.text_list_item, parent, false));
        } else {
            throw new IllegalArgumentException("Wrong view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        ListItem item = values.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    @Override
    public int getItemViewType(int position) {
        ListItem item = values.get(position);
        if (item instanceof FeedViewItem) {
            return R.layout.feed_list_item;
        } else if (item instanceof TextItem) {
            return R.layout.text_list_item;
        } else {
            return R.layout.text_list_item;
        }
    }

    public abstract static class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View view) {
            super(view);
        }

        public abstract void bind(ListItem item);
    }

    public class FeedItemViewHolder extends BaseViewHolder {
        private FwVideoFeedView feedView;

        public FeedItemViewHolder(@NonNull View view) {
            super(view);
        }

        @Override
        public void bind(ListItem item) {
            // This check is needed to avoid calling init() every time view goes in and out of the view port
            if (feedView == null) {
                feedView = itemView.findViewById(R.id.feedView);
                FeedViewItem feedViewItem = (FeedViewItem) item;
                feedView.init(feedViewItem.getViewOptions());
                feedView.setOnErrorListener(onFeedErrorListener);
                feedView.setOnFeedItemClickListener(onFeedItemClickListener);
                feedView.setOnFeedViewStateListener(onFeedViewStateListener);
            }
        }
    }

    public class TextItemViewHolder extends BaseViewHolder {
        private final TextView textView;

        public TextItemViewHolder(@NonNull View view) {
            super(view);
            textView = view.findViewById(R.id.text);
        }

        @Override
        public void bind(ListItem item) {
            TextItem textItem = (TextItem) item;
            textView.setText(textItem.getText());
        }
    }
} 