package com.firewok.example.java_integration.shopping.shoppingcart;

public class ProductUnitItem {
    private final String title;
    private final String subTitle;
    private final String iconUrl;

    public ProductUnitItem(String title, String subTitle, String iconUrl) {
        this.title = title;
        this.subTitle = subTitle;
        this.iconUrl = iconUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getIconUrl() {
        return iconUrl;
    }
} 