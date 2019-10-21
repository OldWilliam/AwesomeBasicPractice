package me.jim.wx.awesomebasicpractice.view.slipconflict;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Date: 2019-09-22
 * Name: wx
 * Description: 滑动冲突，内部拦截法
 */
public class HorizontalSlideConflictView extends HorizontalScrollView {
    private static final String TAG = "HorizontalSlideConflictView";

    public HorizontalSlideConflictView(Context context) {
        super(context);
    }

    public HorizontalSlideConflictView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalSlideConflictView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HorizontalSlideConflictView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent: " + getActionName(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean handle = super.onTouchEvent(ev);
        Log.e(TAG, "onTouchEvent: " + getActionName(ev) + " " + handle);
        return handle;
    }

    private String getActionName(MotionEvent ev) {
        String ret = String.valueOf(ev.getAction());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                ret = "MOVE";
                break;
            case MotionEvent.ACTION_DOWN:
                ret = "DOWN";
                break;
            case MotionEvent.ACTION_UP:
                ret = "UP";
                break;
            case MotionEvent.ACTION_CANCEL:
                ret = "CANCEL";
                break;
            default:
                break;
        }
        return ret;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onInterceptTouchEvent: " + getActionName(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.e(TAG, "requestDisallowInterceptTouchEvent: " + disallowIntercept);
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(600, MeasureSpec.EXACTLY));
    }
}
