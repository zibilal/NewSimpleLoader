package com.zibilal.newsimpleloader.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.zibilal.consumeapi.lib.network.Response;
import com.zibilal.consumeapi.lib.worker.HttpAsyncTask;
import com.zibilal.newsimpleloader.app.database.SimpleCacheObjectHelper;
import com.zibilal.newsimpleloader.app.database.SimpleCacheSqliteHelper;
import com.zibilal.newsimpleloader.app.list.adapter.BaseAdapterHelper;
import com.zibilal.newsimpleloader.app.list.adapter.DefaultBaseAdapter;
import com.zibilal.newsimpleloader.app.list.adapter.ThumbnailedAdapterHelper;
import com.zibilal.newsimpleloader.app.model.DetikNewsListJSONResponse;
import com.zibilal.newsimpleloader.app.model.Entry;
import com.zibilal.newsimpleloader.app.model.NewsItem2;
import com.zibilal.newsimpleloader.app.widget.EndlessListView;

import java.util.ArrayList;
import java.util.List;

public class ConsumeAPITest1Activity extends ActionBarActivity implements EndlessListView.EndlessListener {

    //private ListView mListView;
    private EndlessListView mListView;
    private DefaultBaseAdapter mAdapter;

    private SimpleCacheObjectHelper mCacheObjectHelper;
    private SimpleCacheSqliteHelper mSqliteHelper;

    private static final String TAG="ConsumeAPITest1Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consume_apitest1);

        mListView = (EndlessListView) findViewById(R.id.stackoverflow_listview);

        BaseAdapterHelper helper = new ThumbnailedAdapterHelper(this, Entry.class);
        mAdapter = new DefaultBaseAdapter(helper);
        mListView.setLoadingView(R.layout.item_footer);
        mListView.setAdapter(mAdapter);

        mListView.setListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ConsumeAPITest1Activity.this, "Clicked!!", Toast.LENGTH_SHORT).show();
            }
        });


        try {

            mSqliteHelper = new SimpleCacheSqliteHelper(this, NewsItem2.class);
            mCacheObjectHelper = new SimpleCacheObjectHelper(mSqliteHelper);
            mSqliteHelper.setColumns(mCacheObjectHelper.allColumns(NewsItem2.class));
            mCacheObjectHelper.open();


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFirst();

    }

    @Override
    protected void onPause() {
        mCacheObjectHelper.close();
        super.onPause();
    }

    public void onLoadButton(View view) {
        mAdapter.clear();
        loadFirst();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.consume_apitest1, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadFirst() {
        HttpAsyncTask worker = new HttpAsyncTask(new HttpAsyncTask.OnPostExecute() {
            @Override
            public void onProgress(Integer i) {

            }

            @Override
            public void onUpdate(Response response) {

                /*
                StackOverflowXmlResponse xmlResponse = (StackOverflowXmlResponse) response;
                List<ThumbnailedAdapterHelper.IDataAdapter> entries = (List<ThumbnailedAdapterHelper.IDataAdapter>) xmlResponse.responseData();
                mAdapter.addData(entries); */
                /*
                DetikLatestNewsXmlResponse xmlResponse = (DetikLatestNewsXmlResponse) response;
                List<ThumbnailedAdapterHelper.IDataAdapter> entries = (List<ThumbnailedAdapterHelper.IDataAdapter>) xmlResponse.responseData();
                mListView.addNewData(entries); */


                List<ThumbnailedAdapterHelper.IDataAdapter> entries = (List<ThumbnailedAdapterHelper.IDataAdapter>) response.responseData();
                mListView.setHeaderView(R.layout.item_header, entries.get(0));
                List<ThumbnailedAdapterHelper.IDataAdapter> tempEntries = new ArrayList<ThumbnailedAdapterHelper.IDataAdapter>();
                for(int i=1; i <entries.size();i++) {
                    tempEntries.add(entries.get(i));
                }
                mListView.addNewData(tempEntries);

            }
        },
                //StackOverflowXmlResponse.class);
                //DetikLatestNewsXmlResponse.class);
                DetikNewsListJSONResponse.class, true,
                mCacheObjectHelper,
                "latestnews|50"
        );

        //worker.execute("http://stackoverflow.com/feeds/tag?tagnames=android&sort=newest", HttpClient.XML_TYPE);
        //worker.execute("http://android.detik.com/rss/index_new.php?idkanal=2&type=plain&p=1&ctype=1&compress=1", HttpClient.XML_TYPE);
        worker.execute("http://android.detik.com/api/news?kanal=2");
    }

    @Override
    public void loadData() {
        /*
        HttpAsyncTask worker = new HttpAsyncTask(new HttpAsyncTask.OnPostExecute() {
            @Override
            public void onProgress(Integer i) {

            }

            @Override
            public void onUpdate(Response response) {

                DetikNewsListJSONResponse jsonResponse = (DetikNewsListJSONResponse) response;
                List<ThumbnailedAdapterHelper.IDataAdapter> entries = (List<ThumbnailedAdapterHelper.IDataAdapter>) jsonResponse.responseData();
                mListView.addNewData(entries);

            }
        },
                DetikNewsListJSONResponse.class, true,
                null,
                null
        );

        //worker.execute("http://stackoverflow.com/feeds/tag?tagnames=android&sort=newest", HttpClient.XML_TYPE);
        //worker.execute("http://android.detik.com/rss/index_new.php?idkanal=2&type=plain&p=1&ctype=1&compress=1", HttpClient.XML_TYPE);
        worker.execute("http://android.detik.com/api/news?kanal=2");
         */
    }
}
