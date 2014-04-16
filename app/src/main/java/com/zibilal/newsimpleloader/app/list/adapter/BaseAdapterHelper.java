package com.zibilal.newsimpleloader.app.list.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.zibilal.newsimpleloader.app.model.DataHttpModel;

/**
 * Created by bmuhamm on 3/29/14.
 */
public interface BaseAdapterHelper {
    public void displayView(int position, View convertView, ViewGroup parent, ThumbnailedAdapterHelper.IDataAdapter model);
}
