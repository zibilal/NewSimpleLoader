package com.zibilal.newsimpleloader.app.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmuhamm on 3/12/14.
 */
public class DummyTaskLoader extends AsyncTaskLoader<List<String>>{

    private static final String TAG="DummyTaskLoader";

    private List<String> mData;
    private String mName;
    private boolean mRun;

    public DummyTaskLoader(Context context, String name) {
        super(context);
        mData = new ArrayList<String>();
        mName=name;
        mRun=false;
    }

    public boolean isRun(){
        return mRun;
    }

    public void stop(){
        mRun=false;
    }

    public void start() {mRun = true;}

    @Override
    public List<String> loadInBackground() {

        try{
            while(mRun){
                Thread.sleep(1000);
                Log.d(TAG, "------>> Calling = " + mName + " at " + System.currentTimeMillis());
            }
        } catch(Exception e) {
            Log.e(TAG, "Exception : " + e.getMessage());
        }

        mData = new ArrayList<String>();
        mData.add("Item Satu");
        mData.add("Item Dua");
        mData.add("Item Tiga");
        mData.add("Item Empat");
        mData.add("Item Lima");
        mData.add("Item Enam");
        mData.add("Item Tujuh");
        mData.add("Item Delapan");
        mData.add("Item Sembilan");

        return mData;
    }

    @Override
    public void deliverResult(List<String> data) {
        Log.d(TAG, "------->> Deliver Result");
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        Log.d(TAG, "------->> On Start Loading");
        if(mData!=null && mData.size() > 0) {
            deliverResult(mData);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        Log.d(TAG, "------->> On Stop Loading");
        stop();
        super.onStopLoading();
    }
}
