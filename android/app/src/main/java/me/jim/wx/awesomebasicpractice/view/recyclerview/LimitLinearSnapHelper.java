package me.jim.wx.awesomebasicpractice.view.recyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;

/**
 * Date: 2019/3/5
 * Name: wx
 * Description:
 */
public class LimitLinearSnapHelper extends LinearSnapHelper {

    private int mLimit = 2;

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        int targetSnapPosition = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
        int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        int firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();

        int centerPosition = (lastVisibleItemPosition - firstVisibleItemPosition) / 2 + firstVisibleItemPosition;

        if (Math.abs(centerPosition - targetSnapPosition) > mLimit) {
            targetSnapPosition = centerPosition + mLimit * velocityX / Math.abs(velocityX);
        }

        return targetSnapPosition;
    }
}
