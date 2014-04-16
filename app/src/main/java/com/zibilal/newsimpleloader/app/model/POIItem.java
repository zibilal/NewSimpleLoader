package com.zibilal.newsimpleloader.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.io.OutputStream;

/**
 * Created by bmuhamm on 3/11/14.
 */
public class POIItem implements DataHttpModel{

    private int id;
    private String name;
    private String description;

    public static final Creator<POIItem> CREATOR = new Creator<POIItem>() {
        @Override
        public POIItem createFromParcel(Parcel parcel) {
            return new POIItem(parcel);
        }

        @Override
        public POIItem[] newArray(int size) {
            return new POIItem[size];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

    public POIItem(int id, String name, String description) {
        this.id=id;
        this.name=name;
        this.description=description;
    }

    private POIItem(Parcel in){
        id=in.readInt();
        name=in.readString();
        description=in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public JSONObject toJSON() {
        return null;
    }
}
