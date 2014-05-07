package com.zibilal.newsimpleloader.app.widget;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by bmuhamm on 4/28/14.
 */
public class PagerContainer extends FrameLayout implements ViewPager.OnPageChangeListener {

    private ViewPager mPager;
    boolean mNeedsRedraw = false;

    public PagerContainer(Context context) {
        super(context);
        init();
    }

    public PagerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagerContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        // Disable clipping of children so non-selected pages are visibile
        setClipChildren(false);

        // Child clipping doesn't work with hardware acceleration in Android 3.x/4.x
        // You need to set this value here if using hardware acceleration in an
        // application targeted at these releases.
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onFinishInflate() {
        try{
            mPager = (ViewPager) getChildAt(0);
            mPager.setOnPageChangeListener(this);
        } catch(Exception e){
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
    }

    public ViewPager getViewPager(){
        return mPager;
    }

    private Point mCenter = new Point();
    private Point mInitialTouch = new Point();
    private float mScale;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenter.x = w / 2;
        mCenter.y = h / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Capture any touches not already handled by the ViewPager
        // to implement scrolling from a touche outside the pager bounds.
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitialTouch.x = (int) event.getX();
                mInitialTouch.y = (int) event.getY();
            default:
                event.offsetLocation(mCenter.x - mInitialTouch.x, mCenter.y - mInitialTouch.y);
                break;
        }

        return mPager.dispatchTouchEvent(event);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Force the container to redraw on scrolling
        // Without this the outer pages render initially and then stay static
        if(mNeedsRedraw) invalidate();

        Log.d("PagerContainer", String.format("onPageScrolled position=%d, positionOffset=%.4f, positionOffsetPixels=%d", position, positionOffset, positionOffsetPixels));
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("PageContainer", String.format("onPageSelected position=%d", position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mNeedsRedraw = (state != ViewPager.SCROLL_STATE_IDLE);
        Log.d("PageContainer", String.format("onPageScrollSelected state=%d", state));
    }
}
