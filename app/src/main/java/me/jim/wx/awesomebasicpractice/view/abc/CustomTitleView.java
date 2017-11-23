package me.jim.wx.awesomebasicpractice.view.abc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import org.jetbrains.annotations.Nullable;

/**
 * Created by wx on 2017/11/23.
 *
 * 简单的显示文字
 */

public class CustomTitleView extends View {

    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    private int mBackgroundColor;

    private Paint mPaint;
    private Rect mBound;

    public CustomTitleView(Context context) {
        super(context);
        initView();
    }

    public CustomTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    // TODO: 2017/11/23 什么意思
    public CustomTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // TODO: 2017/11/23 什么意思
    public CustomTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView() {
        mTitleText = "标题啦";
        mTitleTextColor = Color.WHITE;
        mTitleTextSize = 100;
        mBackgroundColor = Color.GRAY;

        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
    }

    /**
     * http://blog.csdn.net/lmj623565791/article/details/24252901
     * <p>
     * 系统帮我们测量的高度和宽度都是MATCH_PARNET，当我们设置明确的宽度和高度时，
     * 系统帮我们测量的结果就是我们设置的结果，当我们设置为WRAP_CONTENT,
     * 或者MATCH_PARENT系统帮我们测量的结果就是MATCH_PARENT的长度。
     * 所以，当设置了WRAP_CONTENT时，我们需要自己进行测量，
     * 即重写onMesure方法”：重写之前先了解MeasureSpec的specMode,一共三种类型：
     * EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
     * AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
     * UNSPECIFIED：表示子布局想要多大就多大，很少使用
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0;
        int height = 0;

        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);

        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);

        if (wMode == MeasureSpec.EXACTLY) {
            width = wSize;
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            width = mBound.width() + getPaddingLeft() + getPaddingRight();
        }

        if (hMode == MeasureSpec.EXACTLY) {
            height = hSize;
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            height = mBound.height() + getPaddingBottom() + getPaddingTop();
        }

        setMeasuredDimension(width, height);


        /**
         * 其实上面那些都可以用resolveSize()替代
         * 为View类的静态方法，封装了测量的方法
         */
//        resolveSize()
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(mBackgroundColor);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2, getHeight()/2 + mBound.height()/2 - getPaddingBottom(), mPaint);
    }
}
