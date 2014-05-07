package com.zibilal.newsimpleloader.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bmuhamm on 4/28/14.
 */
public class DNPhotoDetail implements Parcelable{
    private String cover;
    private String description;
    private String id;
    private String story;

    public DNPhotoDetail(){}

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(cover);
        out.writeString(description);
        out.writeString(id);
        out.writeString(story);
    }

    public static final Creator<DNPhotoDetail> CREATOR = new Creator<DNPhotoDetail>() {
        @Override
        public DNPhotoDetail createFromParcel(Parcel parcel) {
            return new DNPhotoDetail(parcel);
        }

        @Override
        public DNPhotoDetail[] newArray(int size) {
            return new DNPhotoDetail[size];
        }
    };

    private DNPhotoDetail(Parcel in) {
        cover = in.readString();
        description = in.readString();
        id = in.readString();
        story = in.readString();
    }
}
