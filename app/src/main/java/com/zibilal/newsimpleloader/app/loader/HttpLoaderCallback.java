package com.zibilal.newsimpleloader.app.loader;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.zibilal.newsimpleloader.app.model.POIItem;

import java.util.List;

/**
 * Created by bmuhamm on 3/11/14.
 */
public class HttpLoaderCallback implements LoaderManager.LoaderCallbacks<List<POIItem>>{

    @Override
    public Loader<List<POIItem>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<POIItem>> loader, List<POIItem> data) {
        //// testsdfad
    }

    @Override
    public void onLoaderReset(Loader<List<POIItem>> loader) {

    }
}
