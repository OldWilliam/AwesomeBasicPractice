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
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.util.ContextHelper;
import me.jim.wx.awesomebasicpractice.util.UtilsKt;

/**
 * Date: 2019-12-06
 * Name: weixin
 * Description: 拍照时，人脸取景框
 */
public class HumanFaceFinderDrawable extends Drawable {

    private Paint mPaint;
    private final Bitmap mHead;
    private final Bitmap mCard;

    private static final String TAG = HumanFaceFinderDrawable.class.getSimpleName();

    public HumanFaceFinderDrawable(Context context) {
        super();
        mHead = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.shape_head)).getBitmap();
        mCard = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.shape_card)).getBitmap();
        mPaint = new Paint();
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
        float bottom = headRect.bottom - 0.11f * headRect.width();//按比例
        canvas.drawBitmap(mCard, null, new RectF(left, bottom - height, left + width, bottom), mPaint);

    }

    @NotNull
    private RectF getHeadRectF() {
        float horizontalPadding = UtilsKt.dp2Px(ContextHelper.getContext(), 10);
        float realWidth = getBounds().width() - horizontalPadding * 2;
        float realHeight = mHead.getHeight() * 1.0f / mHead.getWidth() * realWidth;

        //noinspection UnnecessaryLocalVariable
        float left = horizontalPadding;//水平居中
        float top = (getBounds().height() - realHeight) / 2.0f;//垂直居中
        return new RectF(left, top, left + realWidth, top + realHeight);
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
