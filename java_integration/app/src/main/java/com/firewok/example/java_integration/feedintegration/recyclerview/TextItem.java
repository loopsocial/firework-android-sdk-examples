package com.firewok.example.java_integration.feedintegration.recyclerview;

public class TextItem implements ListItem {
    private final String text;

    public TextItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
} 