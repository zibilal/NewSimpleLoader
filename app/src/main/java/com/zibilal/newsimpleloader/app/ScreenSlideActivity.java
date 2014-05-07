package com.zibilal.newsimpleloader.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.zibilal.consumeapi.lib.network.Response;
import com.zibilal.consumeapi.lib.worker.HttpAsyncTask;
import com.zibilal.newsimpleloader.app.fragments.ScreenSlidePageFragment;
import com.zibilal.newsimpleloader.app.model.DNPhotoDetail;
import com.zibilal.newsimpleloader.app.model.DNReadPhotosJSONResponse;
import com.zibilal.newsimpleloader.app.widget.ZoomOutPageTransformer;

import java.util.List;


public class ScreenSlideActivity extends ActionBarActivity {

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private List<DNPhotoDetail> mPhotoDetails;

    private ImageView mPrevView;
    private ImageView mNextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        loadData();

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                invalidateOptionsMenu();
                /*
                if(position == (mPagerAdapter.getCount() - 1)) {
                    loadData();
                } */

                int size = mPagerAdapter.getCount() - 1;

                if(position > 0 && position < size) {
                    mNextView.setVisibility(View.VISIBLE);
                    mPrevView.setVisibility(View.VISIBLE);
                } else if (position == 0) {
                    mNextView.setVisibility(View.VISIBLE);
                    mPrevView.setVisibility(View.INVISIBLE);
                } else {
                    mNextView.setVisibility(View.INVISIBLE);
                    mPrevView.setVisibility(View.VISIBLE);
                }
            }
        });

        mViewPager.setPageMargin(20);
        //mViewPager.setPageTransformer(false, new ZoomOutPageTransformer());

        mPrevView = (ImageView) findViewById(R.id.prev_view);
        mPrevView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = mViewPager.getCurrentItem() - 1;
                mViewPager.setCurrentItem(position);
            }
        });
        mNextView = (ImageView) findViewById(R.id.next_view);
        mNextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = mViewPager.getCurrentItem() + 1;
                mViewPager.setCurrentItem(position);
            }
        });

        mNextView.setVisibility(View.VISIBLE);
        mPrevView.setVisibility(View.INVISIBLE);
    }

    private void loadData() {
        HttpAsyncTask worker = new HttpAsyncTask(new HttpAsyncTask.OnPostExecute() {
            @Override
            public void onProgress(Integer i) {

            }

            @Override
            public void onUpdate(Response response) {
                DNReadPhotosJSONResponse jsonResponse = (DNReadPhotosJSONResponse) response;
                if(mPhotoDetails == null || mPhotoDetails.size() == 0) {
                    mPhotoDetails = jsonResponse.getContent().getFotos();
                } else {
                    mPhotoDetails.addAll(jsonResponse.getContent().getFotos());
                }
                mPagerAdapter.notifyDataSetChanged();
            }
        }, DNReadPhotosJSONResponse.class, true);
        String url="http://android.detik.com/api/news_detail?url=http://foto.detik.com/readfoto/2014/04/28/103406/2567115/548/1/chelsea-tundukan-liverpool-di-anfield";
        worker.execute(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.screen_slide, menu);


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

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (mPhotoDetails != null && mPhotoDetails.size() > 0) {
                DNPhotoDetail photoDetail = mPhotoDetails.get(position);
                return ScreenSlidePageFragment.create(photoDetail);
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return mPhotoDetails!=null? mPhotoDetails.size() : 0;
        }
    }

}
