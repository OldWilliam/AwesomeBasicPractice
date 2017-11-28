package me.jim.wx.awesomebasicpractice.view.primary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;


/**
 * Created by wx on 2017/11/27.
 */

public class PullRefreshLayout extends LinearLayout {

    private final static int STATUS_PULLING = 1;
    private final static int STATUS_REFRESHING = 0;
    private final static int STATUS_FINISHED = -1;

    private int mHeaderHeight = 120;
    private int mRefreshSlop = 120;

    private View mHeadView;
    private View mContentView;

    private int mStatus = STATUS_FINISHED;
    private int mTouchSlop;
    private Scroller mScroller;
    private Paint mPaint;
    private Handler mHandler;
    private boolean isEnableRefresh = true;

    public PullRefreshLayout(Context context) {
        super(context);
        init();
    }

    public PullRefreshLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initVariable();
        initView();
    }

    private void initVariable() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mScroller = new Scroller(getContext());
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#F0F0F0"));
        mHandler = new Handler();
    }

    private void initView() {
        setOrientation(VERTICAL);
        mHeadView = generateDefaultHeadView();
        addView(mHeadView, 0);
    }

    private View generateDefaultHeadView() {
        TextView textView = new TextView(getContext());
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(15);
        textView.setBackgroundColor(Color.parseColor("#F0F0F0"));
        textView.setGravity(Gravity.CENTER);
        textView.setText("下拉刷新");
        LayoutParams params = generateDefaultLayoutParams();
        params.width = LayoutParams.MATCH_PARENT;
        params.height = mHeaderHeight;
        textView.setLayoutParams(params);
        return textView;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        ViewGroup.LayoutParams params = mHeadView.getLayoutParams();
        mHeadView.layout(0, -params.height, getMeasuredWidth(), 0);
        mContentView = getChildAt(getChildCount() - 1);
        mContentView.layout(mContentView.getLeft(), 0, mContentView.getRight(), mContentView.getMeasuredHeight() + mHeaderHeight);
        mContentView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mContentView.getScrollY() == 0) {
                    isEnableRefresh = true;
                } else {
                    isEnableRefresh = false;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, -getMeasuredHeight(), getMeasuredWidth(), 0, mPaint);
    }

    private float mCurY;
    private float mLastY;

    private float mDownY;
    private float mMoveY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getRawY();
                return false;
            case MotionEvent.ACTION_MOVE:
                mCurY = ev.getRawY();
                if (isEnableRefresh && mCurY - mDownY > 0 && Math.abs(mCurY - mDownY) > mTouchSlop) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mLastY = mCurY;
                mCurY = event.getRawY();
                if (mLastY < mCurY) {
                    scrollBy(0, -(int) (mCurY - mLastY));
                    mStatus = STATUS_PULLING;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mStatus == STATUS_PULLING) {
                    mCurY = event.getRawY();
                    mMoveY = Math.abs(mCurY - mDownY);
                    if (mMoveY > mRefreshSlop) {
                        startRefresh();
                    } else {
                        endRefresh();
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                mCurY = event.getRawY();
                mDownY = event.getRawY();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void startRefresh() {
        mStatus = STATUS_REFRESHING;
        mScroller.startScroll(0, (int) -mMoveY, 0, (int) mMoveY - mHeaderHeight);
        invalidate();
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                endRefresh();
            }
        }, 1500);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    public void endRefresh() {
        if (mStatus == STATUS_PULLING) {
            mScroller.startScroll(0, (int) -mMoveY, 0, (int) mMoveY);
        }
        if (mStatus == STATUS_REFRESHING) {
            mScroller.startScroll(0, -mHeaderHeight, 0, mHeaderHeight);
        }
        invalidate();
        mStatus = STATUS_FINISHED;
    }
}
