package com.zibilal.newsimpleloader.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zibilal.newsimpleloader.app.widget.PagerContainer;
import com.zibilal.newsimpleloader.app.widget.ZoomOutPageTransformer;

/**
 * Created by bmuhamm on 4/28/14.
 */
public class PagerActivity extends ActionBarActivity {
    PagerContainer mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        mContainer = (PagerContainer) findViewById(R.id.pager_container);

        ViewPager pager = mContainer.getViewPager();
        PagerAdapter adapter = new MyPagerAdapter();
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(adapter.getCount());
        pager.setPageMargin(15);

        // If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        pager.setClipChildren(false);
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            Log.d("MyPagerAdapter", String.format("Instantiate position %d", position));

            TextView view = new TextView(PagerActivity.this);
            view.setText("Item" + position);
            view.setGravity(Gravity.CENTER);
            view.setBackgroundColor(Color.argb(255, position * 50, position * 10, position * 50));

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.d("MyPagerAdapter", String.format("Destroy item at position %d", position));
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            Log.d("MyPagerAdapter", "Is view from object ?" + (view == object) );
            return (view == object);
        }
    }
}
