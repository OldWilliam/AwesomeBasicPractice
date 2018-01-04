package me.jim.wx.awesomebasicpractice.recyclerview.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wx on 2018/1/3.
 */

public class SimpleDecor extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private Drawable mDivider;
    private int space = 20;

    public SimpleDecor() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
    }

    public void setDrawable(Drawable mDivider) {
        this.mDivider = mDivider;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        Rect rect = new Rect();
        int left = 0;
        int right = parent.getWidth();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, rect);
            int top = rect.bottom - mDivider.getIntrinsicHeight();
            int bottom = rect.bottom;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        mDivider.setBounds(0, 0, parent.getWidth(), 100);
        mDivider.draw(c);
        mDivider.setBounds(0,0,10,parent.getHeight());
        mDivider.draw(c);
        mDivider.setBounds(parent.getWidth() - 10,0,parent.getWidth(),parent.getHeight());
        mDivider.draw(c);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = mDivider.getIntrinsicHeight();
    }
}
