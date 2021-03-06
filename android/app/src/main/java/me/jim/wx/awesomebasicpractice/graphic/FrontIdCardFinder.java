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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.util.ContextHelper;
import me.jim.wx.awesomebasicpractice.util.UtilsKt;

/**
 * 身份证
 */
public class FrontIdCardFinder extends Drawable {
    private final Bitmap mHead;
    private final Bitmap mEdge;
    private Paint mPaint;

    public FrontIdCardFinder(Context context) {
        this.mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mHead = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.cover_card_font)).getBitmap();
        mEdge = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.card_edge_line)).getBitmap();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        RectF cardRect = getCardRectF();

        drawViewFinder(canvas, cardRect);
        drawYourHead(canvas, cardRect);
        drawEdge(canvas, cardRect);
        drawText(canvas, cardRect);
    }

    private void drawViewFinder(@NonNull Canvas canvas, RectF cardRect) {
        int layerId = canvas.saveLayer(0, 0, getBounds().width(), getBounds().height(), mPaint);

        mPaint.setColor(Color.BLACK);
        canvas.drawRect(cardRect, mPaint);
        canvas.drawColor(Color.parseColor("#80000000"), PorterDuff.Mode.SRC_OUT);
        canvas.restoreToCount(layerId);
    }

    private void drawText(@NonNull Canvas canvas, RectF cardRect) {
        mPaint.setColor(Color.WHITE);
        float textSize = UtilsKt.dp2Px(ContextHelper.getContext(), 16);
        mPaint.setTextSize(textSize);
        String text = "请放正凭证，并调整好光线";
        float width = mPaint.measureText(text);
        canvas.drawText(text, (getBounds().width() - width) / 2, cardRect.top - textSize, mPaint);
    }

    private void drawYourHead(@NonNull Canvas canvas, RectF cardRect) {
        float wRatio = 110.0f / 345.0f;
        float headWidth = wRatio * cardRect.width();
        float headHeight = mHead.getHeight() * 1.0f / mHead.getWidth() * headWidth;

        RectF headRect = new RectF();
        headRect.right = cardRect.right - 36.0f / 345 * cardRect.width();
        headRect.left = headRect.right - headWidth;
        headRect.top = cardRect.top + headHeight * 0.4f;
        headRect.bottom = headRect.top + headHeight;
        canvas.drawBitmap(mHead, null, headRect, mPaint);
    }

    private void drawEdge(Canvas canvas, RectF rect) {
        float width = rect.width() * 1.1f;
        float height = rect.height() * 1.15f;

        float hPadding = (height - rect.height()) / 2;
        float wPadding = (width - rect.width()) / 2;


        canvas.drawBitmap(mEdge, null, new RectF(rect.left - wPadding, rect.top - hPadding, rect.right + wPadding, rect.bottom + hPadding), mPaint);
    }

    @NotNull
    private RectF getCardRectF() {
        float horizontalPadding = UtilsKt.dp2Px(ContextHelper.getContext(), 15);

        float ratio = 85.6f / 54;
        float width = getBounds().width() - horizontalPadding * 2;
        float height = width / ratio;

        float top = (getBounds().height() - height) / 2.0f;

        return new RectF(horizontalPadding, top, horizontalPadding + width, top + height);
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
        return PixelFormat.TRANSPARENT;
    }
}
