package com.article.core;

/**
 * Created by Amos on 2017/6/8.
 * Desc：
 */

public class MainBean {
    private String title;
    private int iconResId;

    public MainBean() {
    }

    public MainBean(String title, int iconResId) {
        this.title = title;
        this.iconResId = iconResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }
}
