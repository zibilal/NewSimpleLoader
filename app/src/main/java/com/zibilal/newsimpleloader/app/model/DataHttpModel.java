package com.zibilal.newsimpleloader.app.model;

import android.os.Parcelable;

import org.json.JSONObject;

import java.io.OutputStream;

/**
 * Created by bmuhamm on 3/24/14.
 */
public interface DataHttpModel extends Parcelable {

    public JSONObject toJSON();
}
