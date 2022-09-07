package me.jim.wx.awesomebasicpractice.view.abc;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wx on 2017/11/26.
 *
 * 四个View布局在四个角
 */

public class CustomViewGroup extends ViewGroup {
    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int tw = 0;
        int bw = 0;
        int lh = 0;
        int rh = 0;

        for (int i = 0; i < getChildCount(); i++) {

            View childView = getChildAt(i);
            LayoutParams params = childView.getLayoutParams();

            if (i == 0 || i == 1) {
                tw += childView.getMeasuredWidth();
            }

            if (i == 0 || i == 2) {
                lh += childView.getMeasuredHeight();
            }

            if (i == 2 || i == 3) {
                bw += childView.getMeasuredWidth();
            }

            if (i == 1 || i == 3) {
                rh = childView.getMeasuredHeight();
            }
        }

        int width = Math.max(tw, bw);
        int height = Math.max(lh, rh);

        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (i == 0) {
                l = 0;
                t = 0;
                r = childView.getMeasuredWidth();
                b = childView.getMeasuredHeight();
            }
            if (i == 1) {
                l = getMeasuredWidth() - childView.getMeasuredWidth();
                t = 0;
                r = getMeasuredWidth();
                b = childView.getMeasuredHeight();
            }
            if (i == 2) {
                l = 0;
                t = getMeasuredHeight() - childView.getMeasuredHeight();
                r = childView.getMeasuredWidth();
                b = getMeasuredHeight();
            }
            if (i == 3) {
                l = getMeasuredWidth() - childView.getMeasuredHeight();
                t = getMeasuredHeight() - childView.getMeasuredHeight();
                r = getMeasuredWidth();
                b = getMeasuredHeight();
            }
            childView.layout(l, t, r, b);
        }
    }
}
