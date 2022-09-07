package me.jim.wx.awesomebasicpractice.graphic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.util.ContextHelper;
import me.jim.wx.awesomebasicpractice.util.UtilsKt;

/**
 */
public class HumanFaceFinder extends Drawable {

    private Paint mPaint;
    private final Bitmap mHead;
    private final Bitmap mCard;

    private static final String TAG = HumanFaceFinder.class.getSimpleName();

    public HumanFaceFinder(Context context) {
        super();
        mHead = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.shape_head)).getBitmap();
        mCard = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.shape_card)).getBitmap();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.e(TAG, "draw: " + getBounds().width() + " " + getBounds().height());
        Log.e(TAG, "draw: " + getIntrinsicWidth() + " " + getIntrinsicHeight());
        Log.e(TAG, "draw: " + mHead.getWidth() + " " + mHead.getHeight());

        int layerId = canvas.saveLayer(0, 0, getBounds().width(), getBounds().height(), mPaint);

        RectF headRect = getHeadRectF();
        canvas.drawBitmap(mHead, null, headRect, mPaint);

        canvas.drawColor(Color.parseColor("#80000000"), PorterDuff.Mode.SRC_OUT);
        canvas.restoreToCount(layerId);

        float width = 0.6f * headRect.width();
        float height = mCard.getHeight() * 1.0f / mCard.getWidth() * width;
        float left = (getBounds().width() - width) / 2.0f;
        float bottom = getBounds().bottom - getBounds().height() * (0.22839506f);//按比例
        canvas.drawBitmap(mCard, null, new RectF(left, bottom - height, left + width, bottom), mPaint);

        drawText(canvas, headRect);
    }

    private void drawText(@NonNull Canvas canvas, RectF cardRect) {
        mPaint.setColor(Color.WHITE);
        float textSize = UtilsKt.dp2Px(ContextHelper.getContext(), 16);
        mPaint.setTextSize(textSize);
        String text = "请手持身份证，凝视屏幕";
        float width = mPaint.measureText(text);
        canvas.drawText(text, (getBounds().width() - width) / 2, cardRect.top - textSize, mPaint);
    }

    @NotNull
    private RectF getHeadRectF() {
        float horizontalPadding = 0;
        float realWidth = getBounds().width() - horizontalPadding * 2;
        float realHeight = mHead.getHeight() * 1.0f / mHead.getWidth() * realWidth;

        float top = getBounds().height() * (0.14814815f);
        return new RectF(0, top, realWidth, top + realHeight);
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
