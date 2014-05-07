package com.zibilal.newsimpleloader.app.model;

import com.zibilal.newsimpleloader.app.list.adapter.ThumbnailedAdapterHelper;

/**
 * Created by bmuhamm on 4/16/14.
 */
public class Entry implements ThumbnailedAdapterHelper.IDataAdapter {
    private String title;
    private String link;
    private String summary;

    public Entry(String title, String summary, String link) {
        this.title = title;
        this.summary = summary;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void setSubtitle(String subtitle) {
        setLink(subtitle);
    }

    @Override
    public String getSubtitle() {
        return getLink();
    }

    @Override
    public void setThumbnailUrl(String url) {

    }

    @Override
    public String getThumbnailUrl() {
        return null;
    }

    @Override
    public void setDescription(String description) {
        setSummary(description);
    }

    @Override
    public String getDescription() {
        return getSummary();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
