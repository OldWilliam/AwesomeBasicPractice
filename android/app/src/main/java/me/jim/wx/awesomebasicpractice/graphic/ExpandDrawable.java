package me.jim.wx.awesomebasicpractice.graphic;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by wx on 2018/3/21.
 */

public class ExpandDrawable extends Drawable implements Animatable {
    private static final String TAG = "ExpandDrawable";
    private Paint mPaint;
    private ValueAnimator mAnimator;
    private float mFraction;

    public ExpandDrawable() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mAnimator = ValueAnimator.ofInt(Color.BLACK, Color.WHITE);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mAnimator.setDuration(1200);
        mAnimator.setEvaluator(new ArgbEvaluator());
        mAnimator.setInterpolator(new DecelerateInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "onAnimationUpdate: fraction" + animation.getAnimatedFraction());
                mFraction = animation.getAnimatedFraction();
                Log.d(TAG, "onAnimationUpdate: value" + Integer.toHexString((Integer) animation.getAnimatedValue()));
                mPaint.setColor((Integer) animation.getAnimatedValue());
                invalidateSelf();
            }
        });
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        float r = getBounds().height() * 0.5f + (Math.abs(getBounds().height() - getBounds().width())) * mFraction * 0.5f;
        canvas.drawCircle(getBounds().exactCenterX(), getBounds().exactCenterY(), r, mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(getBounds().exactCenterX(), getBounds().exactCenterY(), (getBounds().height() * 0.5f), mPaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    protected boolean onStateChange(int[] state) {
        return false;
    }

    @Override
    public void start() {
        mAnimator.start();
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return true;
    }
}
