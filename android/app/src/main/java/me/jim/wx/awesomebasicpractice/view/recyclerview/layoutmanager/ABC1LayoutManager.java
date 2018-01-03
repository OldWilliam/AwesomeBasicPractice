package me.jim.wx.awesomebasicpractice.view.recyclerview.layoutmanager;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wx on 2018/1/3.
 */

public class ABC1LayoutManager extends RecyclerView.LayoutManager {
    public ABC1LayoutManager(Context context) {

    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private SparseArray<Rect> allItemFrames = new SparseArray<>();

    private int totalH = 0;
    private int verticalOffsetY = 0;
    private int resolveCount = 0;

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout() || getItemCount() == 0) {
            return;
        }
        detachAndScrapAttachedViews(recycler);
        View one = recycler.getViewForPosition(0);
        measureChildWithMargins(one, 0, 0);
        int width = getDecoratedMeasuredWidth(one);
        int height = getDecoratedMeasuredHeight(one);

        int offsetY = 0;
        totalH = 0;
        for (int i = 0; i < getItemCount(); i++) {
            Rect rect = new Rect();
            //记得加上偏移量，不然全部叠在一起了
            rect.set(0, offsetY, width, height + offsetY);
            allItemFrames.put(i, rect);
            totalH += height;
            offsetY += height;
        }

        //还是有点问题, 初始数量为1.5倍整屏数量的item
        resolveCount = getHeight() / height * 3 / 2;
        fill(recycler, state);
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {

        if (state.isPreLayout()) {
            return;
        }

        Rect displayFrame = new Rect(0, verticalOffsetY, getWidth(), getHeight() + verticalOffsetY);

        Rect itemRect = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            itemRect.left = getDecoratedLeft(child);
            itemRect.top = getDecoratedTop(child);
            itemRect.right = getDecoratedRight(child);
            itemRect.bottom = getDecoratedBottom(child);
            if (!Rect.intersects(itemRect, displayFrame)) {
                removeAndRecycleView(child, recycler);
            }
        }

        for (int i = 0; i < getItemCount(); i++) {
            Rect rect = allItemFrames.get(i);
            if (Rect.intersects(rect, displayFrame)) {
                //在这里暂时限制实例化的个数，比较ugly，再想想，通过DisplayFrame计算需要的item数量
                View scrap = recycler.getViewForPosition(i % resolveCount);
                //要先测量
                measureChildWithMargins(scrap, 0, 0);
                //记得add，不然不显示
                addView(scrap);
                //要以现在垂直偏移位置为参考点verticalOffsetY，不然跑出屏幕了，并看不见。。
                layoutDecorated(scrap, rect.left, rect.top - verticalOffsetY, rect.right, rect.bottom - verticalOffsetY);
            }
        }
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int realDy = dy;
        if (verticalOffsetY + dy < 0) {
            realDy = -verticalOffsetY;
        } else if (verticalOffsetY + dy > totalH - getHeight()) {
            realDy = totalH - getHeight() - verticalOffsetY;
        }

        verticalOffsetY += realDy;
        offsetChildrenVertical(-realDy);
        fill(recycler, state);
        return realDy;
    }
}
