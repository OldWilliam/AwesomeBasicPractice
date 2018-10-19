package me.jim.wx.awesomebasicpractice.view.abc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import me.jim.wx.awesomebasicpractice.R;

/**
 * Created by wx on 2017/11/23.
 * <p>
 * 简单的图片+形容词
 */

public class CustomImageView extends View {

    private Bitmap mImage;

    private String mDesc;
    private int mTextColor;
    private int mTextSize;

    private int mBgColor;
    private int mBorderColor;

    private Paint mPaint;
    private Rect mTextBound;

    public CustomImageView(Context context) {
        super(context);
        initView();
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mImage = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        mDesc = "这是这个App的启动图标Launcher";

        mTextColor = Color.BLACK;
        mTextSize = 43;

        mBgColor = Color.GRAY;
        mBorderColor = Color.BLACK;

        mPaint = new Paint();
        mTextBound = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mPaint.setTextSize(mTextSize);
        mPaint.getTextBounds(mDesc, 0, mDesc.length(), mTextBound);

        int width = 0;
        int height = 0;
        if (mImage != null) {
            width = resolveSize(Math.max(mTextBound.width(), mImage.getWidth()), widthMeasureSpec);
            height = resolveSize(mTextBound.height() + mImage.getHeight(), heightMeasureSpec);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //背景
        mPaint.setColor(mBgColor);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);//当然是以自身内部为坐标系了

        //边框
        mPaint.setColor(mBorderColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);//当然是以自身内部为坐标系了

        //文字
        if (mTextBound.width() > getWidth()) {
            TextPaint textPaint = new TextPaint(mPaint);
            //文本超出的话就在末尾加上...
            String desc = TextUtils.ellipsize(mDesc, textPaint, getWidth(), TextUtils.TruncateAt.END).toString();
            canvas.drawText(desc, 0, getHeight(), mPaint);
        } else {
            canvas.drawText(mDesc, (getWidth() - mTextBound.width()) / 2, getHeight(), mPaint);
        }

        //图片
        canvas.drawBitmap(mImage, (getWidth() - mImage.getWidth()) / 2, 0, mPaint);
    }
}
