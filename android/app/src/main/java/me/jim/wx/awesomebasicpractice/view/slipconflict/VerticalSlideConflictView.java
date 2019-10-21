package me.jim.wx.awesomebasicpractice.view.slipconflict;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Date: 2019-09-22
 * Name: wx
 * Description:
 */
public class VerticalSlideConflictView extends ScrollView {

    private int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    public VerticalSlideConflictView(Context context) {
        super(context);
    }

    public VerticalSlideConflictView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalSlideConflictView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public VerticalSlideConflictView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(600, MeasureSpec.EXACTLY), heightMeasureSpec);
    }

    @SuppressLint("SetTextI18n")
    private void initDefault() {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setBackgroundColor(Color.GREEN);
        layout.setGravity(Gravity.CENTER);
        layout.setOrientation(LinearLayout.VERTICAL);

        addView(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        for (int i = 0; i < 15; i++) {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.CENTER);
            textView.setText("ITEM");
            layout.addView(textView, ViewGroup.LayoutParams.MATCH_PARENT, 80);
        }
    }


    private static final String TAG = "VerticalSlideConflictVi";
    private float lastY = 0;
    private float lastX = 0;
    private float dy;
    private float dx;

    boolean handleFlag = false;
    boolean isConsume = false;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent: " + getActionName(ev) + " " + handleFlag + " " + isConsume);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = ev.getY();
                lastX = ev.getX();
                requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                dy = ev.getY() - lastY;
                dx = ev.getX() - lastX;
                ViewParent parent = getParent();

                if (dy != 0 && !canScrollVertically((int) -dy)) {
                    isConsume = false;
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(false);
                    }
                    handleFlag = true;
                }

                if (!handleFlag) {
                    if (Math.abs(dy) >= touchSlop || Math.abs(dx) >= touchSlop) {
                        //这是外层父View需要的手势，横向滑动
                        if (Math.abs(dx) > Math.abs(dy)) {
                            isConsume = false;
                            if (parent != null) {
                                parent.requestDisallowInterceptTouchEvent(false);
                            }
                        } else {//竖向滑动
                            isConsume = true;
                        }
                        handleFlag = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastY = 0;
                lastX = 0;
                dx = 0;
                dy = 0;
                handleFlag = false;
                isConsume = false;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.e(TAG, "requestDisallowInterceptTouchEvent: " + disallowIntercept);

        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onInterceptTouchEvent: " + getActionName(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(ev);
        }
        boolean handle = super.onTouchEvent(ev) && isConsume;
        Log.e(TAG, "onTouchEvent: " + getActionName(ev) + " handle: " + handle);
        return false;
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
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        post(this::initDefault);
    }
}
