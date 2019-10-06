package me.jim.wx.awesomebasicpractice.view.abc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wx on 2017/11/24.
 */

public class CustomCircleBar extends View {

    private int mOneColor = Color.BLUE;
    private int mTwoColor = Color.YELLOW;
    private Paint mPaint = new Paint();

    int sweepAngle = 0;

    public CustomCircleBar(Context context) {
        super(context);
    }

    public CustomCircleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (++sweepAngle == 361) {
                        sweepAngle = 0;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = resolveSize(Integer.MAX_VALUE, widthMeasureSpec);
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStrokeWidth(43);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);


        mPaint.setColor(sweepAngle == 360 ? mOneColor : mTwoColor);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - 43, mPaint);
        mPaint.setColor(sweepAngle == 360 ? mTwoColor : mOneColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(43, 43, getWidth() - 43, getHeight() - 43, 0, sweepAngle, false, mPaint);
        }
    }
}
