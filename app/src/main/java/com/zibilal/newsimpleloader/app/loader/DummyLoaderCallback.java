package com.zibilal.newsimpleloader.app.loader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.Random;

/**
 * Created by bmuhamm on 3/12/14.
 */
public class DummyLoaderCallback implements LoaderManager.LoaderCallbacks<List<String>> {
    private static final String TAG="DummyLoaderCallback";

    public static int LOADER_ID=13;

    public static int COUNTER=0;

    private Context mContext;
    private ArrayAdapter<String> mAdapter;
    private DummyTaskLoader mTaskLoader;

    public DummyLoaderCallback(Context context, ArrayAdapter<String> adapter) {
        mContext = context;
        mAdapter = adapter;
    }

    @Override
    public Loader<List<String>> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "******* On Create Loader, Id " + id);
        mTaskLoader = new DummyTaskLoader(mContext, "Loader " + (++COUNTER) );
        mTaskLoader.start();
        return mTaskLoader;
    }

    public void stopWorker(){
        mTaskLoader.stop();
    }

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> data) {
        Log.d(TAG, "******* On Load Finished, data = " + data);
        for(String str : data) {
            mAdapter.add(str);
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setMessage("Finished!");
        dialog.setPositiveButton("Ok", null);
        dialog.show();
    }

    @Override
    public void onLoaderReset(Loader<List<String>> loader) {
        Log.d(TAG, "******* On Load Reset, Loader =  " + loader);
        mAdapter.clear();
    }
}
