package com.zibilal.newsimpleloader.app.model;

import com.google.gson.annotations.SerializedName;
import com.zibilal.consumeapi.lib.network.Response;

import java.util.List;

/**
 * Created by bmuhamm on 4/28/14.
 */
public class DNReadPhotosJSONResponse implements Response {

    private  Content content;

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @Override
    public Object responseData() {
        return content;
    }

    public static class Content {
        private Authors authors;
        private int comment;
        private String data;
        private DataSet dataset;
        private List<DNPhotoDetail> fotos;
        private Id id;
        private List<DNImageDetail> images;
        private String keyword;
        private int multiplepage;
        private String resume;
        private Titles titles;
        private String url;

        public Authors getAuthors() {
            return authors;
        }

        public void setAuthors(Authors authors) {
            this.authors = authors;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public DataSet getDataset() {
            return dataset;
        }

        public void setDataset(DataSet dataset) {
            this.dataset = dataset;
        }

        public List<DNPhotoDetail> getFotos() {
            return fotos;
        }

        public void setFotos(List<DNPhotoDetail> fotos) {
            this.fotos = fotos;
        }

        public Id getId() {
            return id;
        }

        public void setId(Id id) {
            this.id = id;
        }

        public List<DNImageDetail> getImages() {
            return images;
        }

        public void setImages(List<DNImageDetail> images) {
            this.images = images;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public int getMultiplepage() {
            return multiplepage;
        }

        public void setMultiplepage(int multiplepage) {
            this.multiplepage = multiplepage;
        }

        public String getResume() {
            return resume;
        }

        public void setResume(String resume) {
            this.resume = resume;
        }

        public Titles getTitles() {
            return titles;
        }

        public void setTitles(Titles titles) {
            this.titles = titles;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    static class Authors {
        private String editor;
        private String penulis;
        private String reporter;

        public String getEditor() {
            return editor;
        }

        public void setEditor(String editor) {
            this.editor = editor;
        }

        public String getPenulis() {
            return penulis;
        }

        public void setPenulis(String penulis) {
            this.penulis = penulis;
        }

        public String getReporter() {
            return reporter;
        }

        public void setReporter(String reporter) {
            this.reporter = reporter;
        }
    }

    public static class Id {
        @SerializedName("kanal_id")
        private String kanalId;
        @SerializedName("kanal_parent_id")
        private String kanalParentId;
        @SerializedName("kanal_parent_name")
        private String kanalParentName;
        @SerializedName("news_id")
        private String newsId;

        public String getKanalId() {
            return kanalId;
        }

        public void setKanalId(String kanalId) {
            this.kanalId = kanalId;
        }

        public String getKanalParentId() {
            return kanalParentId;
        }

        public void setKanalParentId(String kanalParentId) {
            this.kanalParentId = kanalParentId;
        }

        public String getKanalParentName() {
            return kanalParentName;
        }

        public void setKanalParentName(String kanalParentName) {
            this.kanalParentName = kanalParentName;
        }

        public String getNewsId() {
            return newsId;
        }

        public void setNewsId(String newsId) {
            this.newsId = newsId;
        }
    }

    public static class DataSet {
        private String created;
        private String label;
        private String type;

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Titles {
        private String subtitle;
        private String title;
        private String url;

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
