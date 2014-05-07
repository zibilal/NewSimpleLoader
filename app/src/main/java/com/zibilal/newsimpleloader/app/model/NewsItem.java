package com.zibilal.newsimpleloader.app.model;

import com.zibilal.newsimpleloader.app.list.adapter.ThumbnailedAdapterHelper;

import java.util.Date;

/**
 * Created by bmuhamm on 4/21/14.
 */
public class NewsItem implements ThumbnailedAdapterHelper.IDataAdapter {
    private String title;
    private String subtitle;
    private String link;
    private String guid;
    private Date publishDate;
    private String reporter;
    private String kanalName;
    private String penulis;
    private String editor;
    private String description;
    private Enclosure enclosure;

    public NewsItem(String title, String subtitle, String link, String guid,
                    Date publishDate, String reporter, String kanalName,
                    String penulis, String editor, String description, Enclosure enclosure) {
        this.title = title;
        this.subtitle=subtitle;
        this.link=link;
        this.guid=guid;
        this.publishDate=publishDate;
        this.reporter=reporter;
        this.kanalName=kanalName;
        this.penulis=penulis;
        this.editor=editor;
        this.description=description;
        this.enclosure=enclosure;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    @Override
    public void setThumbnailUrl(String url) {
        if(enclosure != null) {
            enclosure.setLink(url);
        }
    }

    @Override
    public String getThumbnailUrl() {
        if(enclosure!=null)
            return enclosure.getLink();
        else
            return null;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getKanalName() {
        return kanalName;
    }

    public void setKanalName(String kanalName) {
        this.kanalName = kanalName;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }

    public static class Enclosure {
        private String caption;
        private String link;

        public Enclosure(String caption, String link) {
            this.link=link;
            this.caption=caption;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
