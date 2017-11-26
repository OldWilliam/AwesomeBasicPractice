package me.jim.wx.awesomebasicpractice.view.primary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wx on 2017/11/26.
 *
 * 流式布局，一行排满会自动换行
 * 增加缩放模式，仿Bilibili标签
 */

public class FlowLayout extends ViewGroup {

    private int mItemGap = 0;
    private int mLineMargin = 0;
    private int mLineHeight = 0;
    private boolean isShrink = true;
    private boolean isShowArrow = false;

    private ArrowView mArrow;
    private int mVisibleCount;

    public FlowLayout(Context context) {
        super(context);
        initView();
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mItemGap = 12;
        mLineMargin = 24;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mVisibleCount = getChildCount() - (isShowArrow ? 0 : 1);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int mLineWidth = 0;
        int mLineCount = 1;
        if (getChildCount() > 0) {
            mLineHeight = getChildAt(0).getMeasuredHeight();
        }
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (isShrink && isShowArrow) {
                if (mLineWidth + childView.getMeasuredWidth() + mItemGap + mLineHeight/*araow的宽即是行高*/ > width) {
                    mVisibleCount = i;
                    break;
                }
                mLineWidth = mLineWidth + childView.getMeasuredWidth() + mItemGap;
                continue;
            }
            if (mLineWidth + childView.getMeasuredWidth() > width) {
                mLineWidth = childView.getMeasuredWidth();
                mLineCount++;
                continue;
            } else {
                mLineWidth += childView.getMeasuredWidth();
            }
            if (mLineWidth + mItemGap > width) {
                mLineWidth = 0;
                mLineCount++;
            } else if (i != getChildCount() - 1) {
                mLineWidth += mItemGap;
            }
        }
        int h = resolveSize((mLineCount * mLineHeight + (mLineCount - 1) * mLineMargin), heightMeasureSpec);
        int w = resolveSize(mLineWidth, widthMeasureSpec);
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < mVisibleCount; i++) {
            View childView = getChildAt(i);
            if (i == 0) {
                l = 0;
                t = 0;
                r = l + childView.getMeasuredWidth();
                b = t + mLineHeight;
                childView.layout(l, t, r, b);
                continue;
            }

            //换行
            if (r + childView.getMeasuredWidth() > getMeasuredWidth() || r + mItemGap > getMeasuredWidth()) {
                l = 0;
                t += (mLineHeight + mLineMargin);
                r = l + childView.getMeasuredWidth();
                b = t + mLineHeight;
            } else {
                l = r + mItemGap;
                r = l + childView.getMeasuredWidth();
            }
            childView.layout(l, t, r, b);
        }

        if (isShowArrow) {
            l = getMeasuredWidth() - mLineHeight;
            r = getMeasuredWidth();
            mArrow.layout(l, t, r, b);
        }

        //不可见区域的View就要让它们不可见
        for (int i = mVisibleCount; i < getChildCount() - (isShowArrow ? 1 : 0); i++) {
            getChildAt(i).layout(0,0,0,0);
        }
    }


    public void setArrow(boolean b) {
        isShowArrow = b;
        if (b) {
            if (mArrow == null) {
                mArrow = new ArrowView(getContext());
                addView(mArrow);
            }
        } else {
            if (mArrow != null) {
                removeView(mArrow);
            }
        }

        if (isShowArrow) {
            mArrow.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    isShrink = !isShrink;
                    mArrow.setRotation(isShrink ? 0 : 180);
                    requestLayout();
                }
            });
        }
    }
}
