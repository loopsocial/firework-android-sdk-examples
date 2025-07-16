package com.firewok.example.java_integration.feedintegration.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.firework.common.feed.FeedResource;
import com.firewok.example.java_integration.R;
import com.firework.videofeed.FwVideoFeedView;
import com.firework.viewoptions.BaseOption;
import com.firework.viewoptions.ViewOptions;

public class FeedViewFragment extends Fragment {
    private FwVideoFeedView videoFeedView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoFeedView = view.findViewById(R.id.videoFeedView);


        ViewOptions viewOptions = new ViewOptions.Builder().baseOption(new BaseOption.Builder().feedResource(FeedResource.Discovery.INSTANCE).build()).build();
                
        videoFeedView.init(viewOptions);
    }

    @Override
    public void onDestroyView() {
        if (videoFeedView != null) {
            videoFeedView.destroy();
        }
        super.onDestroyView();
    }

    public static FeedViewFragment newInstance() {
        return new FeedViewFragment();
    }
} 