package com.zibilal.newsimpleloader.app;

import android.content.Context;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;


public class GestureFunActivity extends ActionBarActivity {

    private static final String TAG = "GestureFunActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_fun);

        FrameLayout frame = (FrameLayout) findViewById(R.id.graphics_holder);
        PlayAreaView view = new PlayAreaView(this);
        frame.addView(view);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gesture_fun, menu);
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

    private class PlayAreaView extends View {

        private Bitmap droid;

        private Matrix animateStart;
        private Matrix translate;
        private Interpolator animateInterpolator;

        private long startTime;
        private long endTime;
        private float totalAnimDx;
        private float totalAnimDy;

        private GestureDetector gestures;

        public PlayAreaView(Context context) {
            super(context);
            translate = new Matrix();
            droid = BitmapFactory.decodeResource(getResources(), R.drawable.droid_g);
            gestures = new GestureDetector(GestureFunActivity.this, new GestureListener(this));
        }

        public void onAnimateMove(float dx, float dy, long duration) {
            animateStart = new Matrix(translate);
            animateInterpolator = new OvershootInterpolator();
            startTime = System.currentTimeMillis();
            endTime = startTime + duration;
            totalAnimDx = dx;
            totalAnimDy = dy;
            post(new Runnable() {
                @Override
                public void run() {
                    onAnimateStep();
                }
            });

        }

        private void onAnimateStep() {
            long curTime = System.currentTimeMillis();
            float percentTime=(float) (curTime - startTime) / (float) (endTime - startTime);
            float percentDistance = animateInterpolator.getInterpolation(percentTime);
            float curDx = percentDistance * totalAnimDx;
            float curDy = percentDistance * totalAnimDy;
            translate.set(animateStart);
            onMove(curDx, curDy);

            if(percentTime < 1.0f) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        onAnimateStep();
                    }
                });
            }
        }

        private void onMove(float dx, float dy) {
            translate.postTranslate(dx, dy);
            invalidate();
        }

        public void onResetLocation() {
            translate.reset();
            invalidate();
        }

        public void onSetLocation(float dx, float dy) {
            translate.postTranslate(dx, dy);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(droid, translate, null);
            //Matrix m = canvas.getMatrix();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            return gestures.onTouchEvent(event);
        }
    }

    private class GestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

        PlayAreaView view;

        public GestureListener(PlayAreaView view) {
            this.view = view;
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            Log.d(TAG, "onDown");
            return true;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {
            Log.d(TAG, "onShowPress");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float distanceX, float distanceY) {
            view.onMove(-distanceX, -distanceY);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            Log.d(TAG, "onLongPress");
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float velocityX, float velocityY) {

            final float distanceTimeFactor = 0.4f;
            final float totalDx = (distanceTimeFactor * velocityX / 2);
            final float totalDy = (distanceTimeFactor * velocityY / 2);

            view.onAnimateMove(totalDx, totalDy, (long) (1000 * distanceTimeFactor));
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            Log.d(TAG, "onSingleTapConfirmed");
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {
            Log.d(TAG, "onDoubleTap");
            view.onResetLocation();
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            Log.d(TAG, "onDoubleTapEvent");
            return false;
        }
    }

}
