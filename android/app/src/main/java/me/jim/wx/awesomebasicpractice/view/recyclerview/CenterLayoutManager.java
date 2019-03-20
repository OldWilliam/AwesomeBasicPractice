package me.jim.wx.awesomebasicpractice.view.recyclerview;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Date: 2019/3/5
 * Name: wx
 * Description: 只有手动调用scrollToPosition才会去居中
 */
public class CenterLayoutManager extends LinearLayoutManager {

    @SuppressWarnings("unused")
    public CenterLayoutManager(Context context) {
        super(context);
    }

    @SuppressWarnings("WeakerAccess")
    public CenterLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @SuppressWarnings("unused")
    public CenterLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        RecyclerView.SmoothScroller smoothScroller = new CenterSmoothScroller(recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    private class CenterSmoothScroller extends LinearSmoothScroller {

        CenterSmoothScroller(Context context) {
            super(context);
        }

        @Override
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2);
        }

        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return CenterLayoutManager.this
                    .computeScrollVectorForPosition(targetPosition);
        }
    }
}
