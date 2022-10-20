package me.jim.wx.awesomebasicpractice.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

/**
 * Date: 2019-09-29
 * Name: wx
 * Des: 录制按钮
 */
public class RecordButton extends View {

    private RectF rectF = new RectF();

    public RecordButton(Context context) {
        super(context);
    }

    public RecordButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecordButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("unused")
    public RecordButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private Paint mPaint;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setStrokeWidth(10);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mAnim == null) {
                    initAnim();
                }
                mAnim.start();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mAnim.end();
                isFlash = false;
                sweepAngel = 0;
                invalidate();
                break;

            default:
                break;
        }
        return true;
    }

    private float sweepAngel = 0;
    private ValueAnimator mFlashAnim = null;
    private AnimatorSet mAnim = null;
    private boolean isFlash = false;

    private void initAnim() {
        ValueAnimator mCircleAnim = ValueAnimator.ofFloat(0, 360);
        mCircleAnim.setDuration(800);
        mCircleAnim.addUpdateListener(animation -> {
            sweepAngel = (float) animation.getAnimatedValue();
            invalidate();
        });
        mCircleAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isFlash = true;
            }
        });

        mFlashAnim = ValueAnimator.ofArgb(Color.GRAY, Color.WHITE);
        mFlashAnim.addUpdateListener(it -> invalidate());
        mFlashAnim.setRepeatCount(ValueAnimator.INFINITE);
        mFlashAnim.setRepeatMode(ValueAnimator.RESTART);
        mFlashAnim.setDuration(500);

        mAnim = new AnimatorSet();
        mAnim.play(mCircleAnim).before(mFlashAnim);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isFlash) {
            drawFlash(canvas);
        }
        drawCircleProgress(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        int radius = Math.min(getWidth(), getHeight()) / 2;
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(Color.BLACK);
        paint.setTextSize(dipToPx(12));
        canvas.drawText("Record", radius, radius, paint);
    }

    private int dipToPx(int dip) {
        final DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return (int) (displayMetrics.density * dip + 0.5f);
    }

    private void drawFlash(Canvas canvas) {
        int radius = Math.min(getWidth(), getHeight()) / 2;

        mPaint.setStyle(Paint.Style.FILL);

        float animatedFraction = mFlashAnim.getAnimatedFraction();
        mPaint.setColor((Integer) mFlashAnim.getAnimatedValue());
        canvas.drawCircle(radius, radius, radius + 80 * animatedFraction, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(radius, radius, radius - 10, mPaint);
    }

    private void drawCircleProgress(Canvas canvas) {
        rectF.left = 10;
        rectF.top = 10;
        rectF.right = getWidth() - 10;
        rectF.bottom = getHeight() - 10;

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);
        canvas.drawArc(rectF, -90, sweepAngel, false, mPaint);
    }
}
