package com.zibilal.newsimpleloader.app.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.zibilal.newsimpleloader.app.model.DataHttpModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmuhamm on 3/11/14.
 */
public class HttpTaskLoader extends AsyncTaskLoader<List<DataHttpModel>> {

    private List<DataHttpModel> mData;

    public HttpTaskLoader(Context context) {
        super(context);
        mData = new ArrayList<DataHttpModel>();
    }

    @Override
    public List<DataHttpModel> loadInBackground() {
        return null;
    }

    @Override
    public void deliverResult(List<DataHttpModel> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if(mData != null && mData.size() > 0){
            deliverResult(mData);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }


}
