package com.zibilal.newsimpleloader.app.model;

import com.zibilal.newsimpleloader.app.list.adapter.ThumbnailedAdapterHelper;

/**
 * Created by bmuhamm on 4/22/14.
 */
public class NewsItem2 implements ThumbnailedAdapterHelper.IDataAdapter {

    private String subtitle;
    private String description;
    private String reporter;
    private String title;
    private String penulis;
    private String summary;
    private Images images;
    private String link;
    private String editor;
    private NewsDate newsDate;

    public String getSubtitle() {
        return subtitle;
    }

    @Override
    public void setThumbnailUrl(String url) {
        if(images != null) {
            images.setCover(url);
        }
    }

    @Override
    public String getThumbnailUrl() {
        return images!=null?images.getCover():null;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public NewsDate getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(NewsDate newsDate) {
        this.newsDate = newsDate;
    }

    public static class Images {
        private String caption;
        private String story;
        private String cover;

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getStory() {
            return story;
        }

        public void setStory(String story) {
            this.story = story;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
    }

    public static class NewsDate {
        private String publish;
        private String created;

        public String getPublish() {
            return publish;
        }

        public void setPublish(String publish) {
            this.publish = publish;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }
}
