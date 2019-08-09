package me.jim.wx.awesomebasicpractice.view.primary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import butterknife.ButterKnife;

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
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int mLineWidth = 0;
        int mLineCount = 1;
        if (getChildCount() > 0) {
            mLineHeight = getChildAt(0).getMeasuredHeight();
        }
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
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
        for (int i = 0; i < getChildCount(); i++) {
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
    }
}
