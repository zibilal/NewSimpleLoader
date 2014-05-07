package com.zibilal.newsimpleloader.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView mListView;
    private List<TheClass> mList;

    private static class TheClass {
        private String name;
        private Class<? extends Activity> theClass;

        public TheClass(String name, Class<? extends Activity> clss) {
            this.name = name;
            theClass = clss;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Class<? extends Activity> getTheClass() {
            return theClass;
        }

        public void setTheClass(Class<? extends Activity> theClass) {
            this.theClass = theClass;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = new ArrayList<TheClass>();
        initClass();
        ArrayAdapter<TheClass> adapter = new ArrayAdapter<TheClass>(this, android.R.layout.simple_list_item_1, mList);
        mListView=(ListView) findViewById(R.id.list_view);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TheClass dClass = (TheClass) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, dClass.getTheClass());
                startActivity(intent);
            }
        });
    }

    private void initClass() {
        mList.add(new TheClass("DummyActivity", DummyActivity.class));
        mList.add(new TheClass("SingleDrawerLayout", SingleDrawerLayout.class));
        mList.add(new TheClass("ConsumeAPITest1Activity", ConsumeAPITest1Activity.class));
        mList.add(new TheClass("ScreenSlideActivity", ScreenSlideActivity.class));
        mList.add(new TheClass("PagerActivity", PagerActivity.class));
        mList.add(new TheClass("GestureFunActivity", GestureFunActivity.class));
        mList.add(new TheClass("ExtendedViewPagerTestActivity", ExtendedViewPagerTestActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

}
