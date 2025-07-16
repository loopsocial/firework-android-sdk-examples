package com.firewok.example.java_integration.feedintegration.recyclerview;

import com.firework.viewoptions.ViewOptions;

public class FeedViewItem implements ListItem {
    private final String id;
    private final ViewOptions viewOptions;

    public FeedViewItem(String id, ViewOptions viewOptions) {
        this.id = id;
        this.viewOptions = viewOptions;
    }

    public String getId() {
        return id;
    }

    public ViewOptions getViewOptions() {
        return viewOptions;
    }
} 