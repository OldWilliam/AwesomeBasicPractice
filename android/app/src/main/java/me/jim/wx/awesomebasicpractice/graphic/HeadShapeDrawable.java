package me.jim.wx.awesomebasicpractice.graphic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Date: 2019-12-06
 * Name: weixin
 */
public class HeadShapeDrawable extends Drawable {

    private Paint mPaint;
    private Bitmap mBitmap;

    private static final String TAG = "HeadShapeDrawable";

    public HeadShapeDrawable(Bitmap bitmap) {
        super();
        mBitmap = bitmap;
        mPaint = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        Log.e(TAG, "draw: " + canvas.getWidth() + " " + canvas.getHeight());
        Log.e(TAG, "draw: " + getIntrinsicWidth() + " " + getIntrinsicHeight());
        Log.e(TAG, "draw: " + mBitmap.getWidth() + " " + mBitmap.getHeight());
        int layerId = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint);


        canvas.drawBitmap(mBitmap, null, new RectF(0, 0,  mBitmap.getWidth() * 1.0f / mBitmap.getHeight() * canvas.getWidth(), canvas.getHeight()), mPaint);


        canvas.drawColor(Color.parseColor("#80000000"), PorterDuff.Mode.SRC_OUT);

        canvas.restoreToCount(layerId);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }
}
