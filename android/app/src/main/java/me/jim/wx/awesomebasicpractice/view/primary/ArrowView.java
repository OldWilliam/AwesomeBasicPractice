package me.jim.wx.awesomebasicpractice.view.primary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wx on 2017/11/27.
 *
 * 一个箭头
 */

public class ArrowView extends View {

    private Paint mPaint;

    public ArrowView(Context context) {
        super(context);
        initView();
    }

    public ArrowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(6);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int padding = 12;
        canvas.drawLine(0 + padding, getHeight() / 4, getWidth() / 2, getHeight() / 4 * 3 - 12, mPaint);
        canvas.drawLine(getWidth() / 2, getHeight() / 4 * 3 - 12, getWidth() - padding, getHeight() / 4, mPaint);
    }
}
