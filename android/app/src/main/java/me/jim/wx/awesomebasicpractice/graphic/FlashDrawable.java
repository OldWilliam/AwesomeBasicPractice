package me.jim.wx.awesomebasicpractice.graphic;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.animation.AccelerateInterpolator;

/**
 * Date: 2019-11-19
 * Name: weixin
 * Description: 矩形闪烁动画
 */
public class FlashDrawable extends Drawable implements Animatable {

    private final int mMargin;
    private final int mRadius;

    private ValueAnimator mFlashAnim;
    private Paint mPaint;

    private RectF mMeasureRect;

    public FlashDrawable(RectF mRect) {
        mMeasureRect = mRect;

        mMargin = 50;
        mRadius = 30;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mFlashAnim = new ValueAnimator();
        mFlashAnim.setIntValues(Color.RED, Color.TRANSPARENT);
        mFlashAnim.setEvaluator(new ArgbEvaluator());
        mFlashAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPaint.setColor((Integer) animation.getAnimatedValue());
                invalidateSelf();
            }
        });
        mFlashAnim.setInterpolator(new AccelerateInterpolator());
        mFlashAnim.setRepeatCount(ValueAnimator.INFINITE);
        mFlashAnim.setRepeatMode(ValueAnimator.RESTART);
        mFlashAnim.setDuration(500);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);

        float animatedFraction = mFlashAnim.getAnimatedFraction();
        mPaint.setColor((Integer) mFlashAnim.getAnimatedValue());

        RectF rectF = new RectF();
        float diff = mMargin * animatedFraction;
        rectF.left = mMeasureRect.left - diff;
        rectF.top = mMeasureRect.top - diff;
        rectF.right = mMeasureRect.right + diff;
        rectF.bottom = mMeasureRect.bottom + diff;

        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawRoundRect(rectF, mRadius, mRadius, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }


    @Override
    public void start() {
        if (mFlashAnim != null) {
            mFlashAnim.start();
        }
    }

    @Override
    public void stop() {
        if (mFlashAnim != null) {
            mFlashAnim.cancel();
        }
    }

    @Override
    public boolean isRunning() {
        return mFlashAnim != null && mFlashAnim.isRunning();
    }
}
