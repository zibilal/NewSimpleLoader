package com.zibilal.newsimpleloader.app.database;

/**
 * Created by bmuhamm on 5/6/14.
 */
public class Comment {
    private long id;
    private String comment;

    public Comment(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return comment;
    }
}
