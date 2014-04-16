package com.zibilal.newsimpleloader.app.loader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.BaseAdapter;

import com.zibilal.newsimpleloader.app.model.DataHttpModel;
import com.zibilal.newsimpleloader.app.model.POIItem;

import java.util.List;

/**
 * Created by bmuhamm on 3/11/14.
 */
public class HttpLoaderCallback implements LoaderManager.LoaderCallbacks<List<DataHttpModel>>{

    private static final String TAG = "HttpLoaderCallback";


    private int loaderId;
    private Context mContext;
    @Override
    public Loader<List<DataHttpModel>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<DataHttpModel>> loader, List<DataHttpModel> data) {
        //// testsdfad
    }

    @Override
    public void onLoaderReset(Loader<List<DataHttpModel>> loader) {

    }
}
