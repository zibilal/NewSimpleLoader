package com.zibilal.newsimpleloader.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bmuhamm on 4/28/14.
 */
public class DNImageDetail implements Parcelable {
    private String caption;
    private String cover;
    private String description;
    private String title;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(caption);
        out.writeString(cover);
        out.writeString(description);
        out.writeString(title);
    }

    public static final Creator<DNImageDetail> CREATOR = new Creator<DNImageDetail>() {
        @Override
        public DNImageDetail createFromParcel(Parcel in) {
            return new DNImageDetail(in);
        }

        @Override
        public DNImageDetail[] newArray(int size) {
            return new DNImageDetail[size];
        }
    };

    private DNImageDetail(Parcel in) {
        caption = in.readString();
        cover = in.readString();
        description = in.readString();
        title = in.readString();
    }
}
