package me.jim.wx.awesomebasicpractice.widget.CommentsBar;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by wx on 2018/3/1.
 */

public class CommentsBar extends LinearLayout {
    private int x = 0;

    public CommentsBar(Context context) {
        super(context);
        initView();
    }

    public CommentsBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setOrientation(HORIZONTAL);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getX();
                fillStart();
                break;
            case MotionEvent.ACTION_MOVE:
                x = (int) event.getX();
                getParent().requestDisallowInterceptTouchEvent(true);
                fillStart();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    private void fillStart() {
        Rect rect = new Rect(0, 0, x, getHeight());
        Rect childRect = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            TriangleView child = (TriangleView) getChildAt(i);
            childRect.left = child.getLeft();
            childRect.top = child.getTop();
            childRect.right = child.getRight();
            childRect.bottom = child.getBottom();
            if (rect.contains(childRect)) {
                child.setSelect(true);
            } else if (childRect.contains(x, 0)) {
                child.setSelect(true);
            }else {
                child.setSelect(false);
            }
        }
    }
}
