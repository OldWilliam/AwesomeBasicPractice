package me.jim.wx.awesomebasicpractice.graphic;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by wx on 2018/3/30.
 */

public class QuestionMarkDrawable extends Drawable {
    private Paint mPaint;

    public QuestionMarkDrawable() {
        mPaint = new Paint(Color.RED);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        //画个圈
        mPaint.setColor(Color.WHITE);
        Rect rect = getBounds();
        float strokeW = 2;
        mPaint.setStrokeWidth(strokeW);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(rect.exactCenterX(), rect.exactCenterY(), rect.width() / 2 - strokeW, mPaint);
        mPaint.setStyle(Paint.Style.FILL);

        //写个问号
        mPaint.setTextSize(rect.width() - 2 * strokeW);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(1);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float fontH = fontMetrics.bottom - fontMetrics.top;
        float fontW = mPaint.measureText("?");
        float baseLineY = rect.height() - (rect.height() - fontH) / 2 - fontMetrics.bottom;
        float baseLineX = (rect.width() - fontW) / 2;
        canvas.drawText("?", baseLineX, baseLineY, mPaint);
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
}
