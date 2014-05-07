package com.zibilal.newsimpleloader.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;


public class ImageActivity extends ActionBarActivity {

    public static final String EXT_PHOTO="photo";

    private ImageViewTouch mPhotoView;

    private ScaleGestureDetector sgd;
    private GestureDetectorCompat mGestureDetectorCompat;

    private float scale=1f;
    private Matrix matrix = new Matrix();


    private static final float AXIS_X_MIN=-1f;
    private static final float AXIS_Y_MIN=-1f;
    private static final float AXIS_X_MAX=1f;
    private static final float AXIS_Y_MAX=1f;

    private RectF mCurrentViewport = new RectF(AXIS_X_MIN, AXIS_Y_MIN, AXIS_X_MAX, AXIS_Y_MAX);
    private Rect mContentRect = new Rect();

    private final GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // Scrolling uses math based on the viewport ( as opposed to math using pixels )

            // Pixel offset is the offset in screen pixels, while viewport offset is the
            // offset within the current viewport.
            float viewportOffsetX = distanceX * mCurrentViewport.width() / mContentRect.width();
            float viewportOffsetY = -distanceY * mCurrentViewport.height() / mContentRect.height();


            // Updates  the viewport, refreshes the display.
            setViewportBottomLeft(mCurrentViewport.left + viewportOffsetX, mCurrentViewport.bottom + viewportOffsetY);


            return true;
        }
    };

    /**
     * Set the current viewport (defined by mCurrentViewport) to the given X and Y positions.
     * Note that the Y value represents the topmost pixel position,
     * and thus the bottom of the mCurrentViewport rectangle.
     * @param x
     * @param y
     */

    private void setViewportBottomLeft(float x, float y) {
        float curWidth = mCurrentViewport.width();
        float curHeight = mCurrentViewport.height();
        x = Math.max(AXIS_X_MIN, Math.min(x, AXIS_X_MAX - curWidth));
        y = Math.max(AXIS_Y_MIN + curHeight, Math.min(y, AXIS_Y_MAX));

        mCurrentViewport.set(x, y - curHeight, x + curWidth, y);
        ViewCompat.postInvalidateOnAnimation(mPhotoView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mPhotoView = (ImageViewTouch) findViewById(R.id.the_image);

        Bundle extras = getIntent().getExtras();
        byte[] rawByte = extras.getByteArray(EXT_PHOTO);
        Bitmap bitmap = BitmapFactory.decodeByteArray(rawByte, 0, rawByte.length);

        Matrix imageMatrix = new Matrix();
        mPhotoView.setImageBitmap(bitmap, imageMatrix.isIdentity() ? null : imageMatrix, ImageViewTouchBase.ZOOM_INVALID, ImageViewTouchBase.ZOOM_INVALID );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image, menu);
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

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();
            scale = Math.max(0.1f, Math.min(scale, 5.0f));
            matrix.setScale(scale, scale);

            mPhotoView.setImageMatrix(matrix);
            return true;
        }
    }

}
