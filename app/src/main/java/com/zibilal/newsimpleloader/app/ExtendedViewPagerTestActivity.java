package com.zibilal.newsimpleloader.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zibilal.consumeapi.lib.network.HttpClient;
import com.zibilal.consumeapi.lib.network.Response;
import com.zibilal.consumeapi.lib.rawbyte.ResponseRaw;
import com.zibilal.consumeapi.lib.worker.HttpAsyncTask;
import com.zibilal.newsimpleloader.app.fragments.ScreenSlidePageFragment;
import com.zibilal.newsimpleloader.app.model.DNPhotoDetail;
import com.zibilal.newsimpleloader.app.model.DNReadPhotosJSONResponse;
import com.zibilal.newsimpleloader.app.widget.ExtendedViewPager;
import com.zibilal.newsimpleloader.app.widget.TouchImageView;

import java.util.ArrayList;
import java.util.List;


public class ExtendedViewPagerTestActivity extends ActionBarActivity {

    //private TouchImageAdapter mPagerAdapter;

    private TouchImageFragmentAdapter mPagerAdapter;
    private ImageView mPrevView;
    private ImageView mNextView;
    private ExtendedViewPager viewPager;

    private List<DNPhotoDetail> mPhotoDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extended_view_pager_test);

        mPhotoDetails = new ArrayList<DNPhotoDetail>();

        viewPager = (ExtendedViewPager) findViewById(R.id.view_pager);
        /*
        mPagerAdapter = new TouchImageAdapter();
        viewPager.setAdapter(mPagerAdapter);
        */

        mPagerAdapter = new TouchImageFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
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

        viewPager.setPageMargin(20);

        mPrevView = (ImageView) findViewById(R.id.prev_view);
        mNextView = (ImageView) findViewById(R.id.next_view);

        mNextView.setVisibility(View.VISIBLE);
        mPrevView.setVisibility(View.INVISIBLE);

        loadData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.extended_view_pager_test, menu);
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

    private void loadData() {
        HttpAsyncTask worker = new HttpAsyncTask(new HttpAsyncTask.OnPostExecute() {
            @Override
            public void onProgress(Integer i) {

            }

            @Override
            public void onUpdate(Response response) {
                DNReadPhotosJSONResponse jsonResponse = (DNReadPhotosJSONResponse) response;
                //mPagerAdapter.addPhotoDetails(jsonResponse.getContent().getFotos());

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

    private class TouchImageFragmentAdapter extends FragmentStatePagerAdapter {

        public TouchImageFragmentAdapter(FragmentManager fm) {
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

    static class TouchImageAdapter extends PagerAdapter {

        private List<DNPhotoDetail> mPhotoDetails;

        public TouchImageAdapter() {
            mPhotoDetails = new ArrayList<DNPhotoDetail>();
        }

        public void addPhotoDetails(List<DNPhotoDetail> photoDetails) {
            mPhotoDetails.addAll(photoDetails);
            notifyDataSetChanged();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TouchImageView imgView = new TouchImageView(container.getContext());
            imgView.setImageResource(R.drawable.default_image);
            container.addView(imgView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            final TouchImageView finalImgView = imgView;

            DNPhotoDetail photoDetail = mPhotoDetails.get(position);

            HttpAsyncTask httpAsyncTask = new HttpAsyncTask(new HttpAsyncTask.OnPostExecute() {
                @Override
                public void onProgress(Integer i) {

                }

                @Override
                public void onUpdate(Response response) {
                    ResponseRaw raw = (ResponseRaw) response;
                    byte[] rawByte = (byte[]) raw.responseData();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(rawByte, 0, rawByte.length);
                    finalImgView.setImageBitmap(bitmap);

                }
            }, ResponseRaw.class, false);
            httpAsyncTask.execute(photoDetail.getStory(), HttpClient.BYTE_TYPE);

            return imgView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mPhotoDetails.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
