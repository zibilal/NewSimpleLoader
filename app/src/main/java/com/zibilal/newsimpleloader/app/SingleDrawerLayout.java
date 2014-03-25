package com.zibilal.newsimpleloader.app;

import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.zibilal.newsimpleloader.app.list.adapter.MenusAdapter;

import java.util.ArrayList;
import java.util.List;


public class SingleDrawerLayout extends ActionBarActivity {

    private ListView mLeftListView;
    private ListView mRightListView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;

    private MenusAdapter mAdapter;
    private MenusAdapter mRightAdapter;

    static String[] menus={
            "Item Satu", "Item Dua", "Item Tiga", "Item Empat", "Item Lima", "Item Enam",
            "Item Tujuh", "Item Delapan", "Item Sembilan", "Item Sepuluh"
    };

    static String[] menus2 = {
            "Right Satu", "Right Dua", "Right Tiga", "Right Empat", "Right Lima", "Right Enam",
            "Right Tujuh"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_drawer_layout);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mTitle = mDrawerTitle = getTitle();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.i("ActionBarDrawerToggle", "Drawer is closed");
                getSupportActionBar().setTitle(mTitle);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.i("ActionBarDrawerToggle", "Drawer is opened");
                getSupportActionBar().setTitle(mDrawerTitle);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        mLeftListView = (ListView) findViewById(R.id.left_drawer);
        mRightListView = (ListView) findViewById(R.id.right_drawer);


        List<String> menuItems = new ArrayList<String>();
        for(String str : menus) {
            menuItems.add(str);
        }

        List<String> menuItems2 = new ArrayList<String>();
        for(String str : menus2) {
            menuItems2.add(str);
        }

        mAdapter = new MenusAdapter(this, menuItems);
        mLeftListView.setAdapter(mAdapter);

        mRightAdapter = new MenusAdapter(this, menuItems2);
        mRightListView.setAdapter(mRightAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.single_drawer_layout, menu);
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
        } else if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
