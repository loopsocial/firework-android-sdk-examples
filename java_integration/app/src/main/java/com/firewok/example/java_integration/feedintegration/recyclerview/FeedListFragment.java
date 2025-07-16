package com.firewok.example.java_integration.feedintegration.recyclerview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firework.common.feed.FeedResource;
import com.firework.error.FwError;
import com.firework.error.FwErrorListener;
import com.firewok.example.java_integration.BuildConfig;
import com.firewok.example.java_integration.R;
import com.firework.videofeed.FeedItemClickListener;
import com.firework.videofeed.FeedViewState;
import com.firework.videofeed.FeedViewStateListener;
import com.firework.viewoptions.BaseOption;
import com.firework.viewoptions.ViewOptions;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FeedListFragment extends Fragment implements FeedItemClickListener, FwErrorListener, FeedViewStateListener {
    private static final String TAG = "FeedListFragment";
    private static final String LOREM_IPSUM = " Lorem Ipsum is simply dummy text of the printing and typesetting industry.\n" +
            "            Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,\n" +
            "            when an unknown printer took a galley of type and scrambled it to make a type specimen book.\n" +
            "            It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.\n" +
            "            It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop\n" +
            "            publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rvList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new FeedListAdapter(getListItems(), getLifecycle(), this, this, this));
    }

    private List<ListItem> getListItems() {

        ViewOptions playlistFeedViewOptions = new ViewOptions.Builder().baseOption(new BaseOption.Builder().feedResource(new FeedResource.Playlist(BuildConfig.FW_CHANNEL_ID, BuildConfig.FW_PLAYLIST_ID)).build()).build();

        return Arrays.asList(
                new TextItem(LOREM_IPSUM),
                new TextItem(LOREM_IPSUM),
                new FeedViewItem(UUID.randomUUID().toString(), playlistFeedViewOptions),
                new TextItem(LOREM_IPSUM),
                new TextItem(LOREM_IPSUM),
                new TextItem(LOREM_IPSUM)
        );
    }

    @Override
    public void onItemClicked(@NonNull FeedItemClickListener.FeedItem feedItem) {
        Log.d(TAG, "onFeedItemClicked: " + feedItem);
    }

    @Override
    public void onLoadStateChanged(@NonNull FeedViewState feedViewState) {
        Log.d(TAG, "Feed feedViewState: " + feedViewState);
    }

    @Override
    public void onFwError(@NonNull FwError error) {
        Log.d(TAG, "onFwError: " + error);
    }

    public static FeedListFragment newInstance() {
        return new FeedListFragment();
    }
} 