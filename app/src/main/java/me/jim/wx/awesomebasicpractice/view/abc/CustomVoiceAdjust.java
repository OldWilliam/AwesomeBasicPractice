package me.jim.wx.awesomebasicpractice.view.abc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import org.jetbrains.annotations.Nullable;

/**
 * Created by wx on 2017/11/26.
 */

public class CustomVoiceAdjust extends View {

    private int mBgColor = Color.GRAY;
    private int mLineForeground = Color.BLACK;
    private int mLineBackGround = Color.BLUE;

    private Paint mPaint;
    private int mCount = 0;

    public CustomVoiceAdjust(Context context) {
        super(context);
        initView();
    }

    public CustomVoiceAdjust(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount++;
                invalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = resolveSize(Integer.MAX_VALUE, widthMeasureSpec);
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(mBgColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, getRight(), getBottom(), mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPaint.setColor(mLineBackGround);
        drawOval(canvas, 12);

        mPaint.setColor(mLineForeground);
        drawOval(canvas, mCount);

    }

    private void drawOval(Canvas canvas, int count) {
        int left = getWidth() / 4;
        int top = getHeight() / 4;
        int right = getWidth() / 4 * 3;
        int bottom = getHeight() / 4 * 3;
        int step = 15;
        float startAngle = 120;
        int sweepAngle = 300;
        float gap = (sweepAngle - 15 * 12) / 11f;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (int i = 0; i < count; i++) {
                canvas.drawArc(left, top, right, bottom, startAngle, step, false, mPaint);
                startAngle = startAngle + step + gap;
            }
        }
    }
}
