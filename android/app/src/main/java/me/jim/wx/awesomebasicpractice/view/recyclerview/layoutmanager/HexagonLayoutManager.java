package me.jim.wx.awesomebasicpractice.view.recyclerview.layoutmanager;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by wx on 2017/12/18.
 * <p>
 * 六边形，环形布局
 */

public class HexagonLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    private int verticalOffset = 0;
    private int horizontalOffset = 0;
    private int itemWidth;
    private int itemHeight;
    private int maxLevel = 6;
    private SparseArray<Rect> allItems = new SparseArray<>();

    private Point center;
    private Rect centerRect;

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout() || getItemCount() == 0) {
            return;
        }

        detachAndScrapAttachedViews(recycler);

        //获取元数据
        View zero = recycler.getViewForPosition(0);
        measureChildWithMargins(zero, 0, 0);
        itemWidth = getDecoratedMeasuredWidth(zero);
        itemHeight = getDecoratedMeasuredHeight(zero);

        center = new Point(getWidth() / 2 + horizontalOffset, getHeight() / 2 + verticalOffset);
        centerRect = new Rect(center.x - itemWidth / 2, center.y - itemHeight / 2, center.x + itemWidth / 2, center.y + itemHeight / 2);

        for (int i = 0; i < maxLevel; i++) {
            layoutNChildren(recycler, state, i);
        }

        fill(recycler, state);
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {

        center = new Point(getWidth() / 2 + horizontalOffset, getHeight() / 2 + verticalOffset);
        centerRect = new Rect(center.x - itemWidth / 2, center.y - itemHeight / 2, center.x + itemWidth / 2, center.y + itemHeight / 2);

        int realLevel = 2;

        Rect displayFrame = new Rect(
                centerRect.left - realLevel * itemWidth,
                centerRect.top - realLevel * itemHeight,
                centerRect.right + realLevel * itemWidth,
                centerRect.bottom + realLevel * itemHeight);

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
            Rect rect = allItems.get(i);
            if (Rect.intersects(rect, displayFrame)) {
                View item = recycler.getViewForPosition(i % 20);
                measureChildWithMargins(item, 0, 0);
                addView(item);
                layoutDecorated(item,
                        rect.left - horizontalOffset,
                        rect.top - verticalOffset,
                        rect.right - horizontalOffset,
                        rect.bottom - verticalOffset);
            }
        }
    }

    private int count = 0;

    @SuppressLint("NewApi")
    private void layoutNChildren(RecyclerView.Recycler recycler, RecyclerView.State state, int N) {

        Point origin = new Point(-N, -N);
        int r = 2 * N + 1;
        int[] xs = new int[r];
        int[] ys = new int[r];

        //生成所有坐标轴上的点
        for (int i = 0; i < r; i++) {
            xs[i] = origin.x + i;
            ys[i] = origin.y + i;
        }

        //生成坐标系上确定的点
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < r; j++) {
                if ((Math.abs(xs[i]) < N) && (Math.abs(ys[j]) < N)) {

                } else {
                    points.add(new Point(xs[i], ys[j]));
                }
            }
        }

        points.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if ((o1.x + o1.y) == (o2.x + o2.y)) {
                    return o1.x - o2.x;
                } else {
                    return ((o1.x + o1.y) - (o2.x + o2.y));
                }
            }
        });

        //添加View
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            Rect childRect = new Rect();
            childRect.left = centerRect.left + p.x * centerRect.width();
            childRect.top = centerRect.top + p.y * centerRect.height();
            childRect.right = centerRect.left + p.x * centerRect.width() + centerRect.width();
            childRect.bottom = centerRect.top + p.y * centerRect.height() + centerRect.height();
            if (count > getItemCount()) {
                break;
            }
            allItems.put(count++, childRect);
        }
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int realDx = dx;
        offsetChildrenHorizontal(-realDx);
        horizontalOffset += realDx;
        fill(recycler, state);
        return realDx;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int realDy = dy;
        offsetChildrenVertical(-realDy);
        verticalOffset += realDy;
        fill(recycler, state);
        return realDy;
    }
}
