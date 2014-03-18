package com.zibilal.newsimpleloader.app;

import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zibilal.newsimpleloader.app.loader.DummyLoaderCallback;


public class DummyActivity extends ActionBarActivity {


    private static final String TAG="DummyActivity";
    private ArrayAdapter<String> mDummyAdapter;

    private ListView mListView;
    private DummyLoaderCallback mLoaderCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        mListView = (ListView) findViewById(R.id.listView);
        mDummyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mLoaderCallback = new DummyLoaderCallback(this, mDummyAdapter);
        mListView.setAdapter(mDummyAdapter);

        final Loader<String> loader = getSupportLoaderManager().getLoader(DummyLoaderCallback.LOADER_ID);
        if(loader != null && loader.isReset()) {
            Log.d(TAG, "****************Restart Loader*****************");
            getSupportLoaderManager().restartLoader(DummyLoaderCallback.LOADER_ID, null, mLoaderCallback);
        } else {
            Log.d(TAG, "****************Start Fresh Loader*************");
            getSupportLoaderManager().initLoader(DummyLoaderCallback.LOADER_ID, null, mLoaderCallback);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dummy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if( id == R.id.action_stop_worker){
            mLoaderCallback.stopWorker();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
