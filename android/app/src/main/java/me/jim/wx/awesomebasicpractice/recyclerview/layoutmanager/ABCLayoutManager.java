package me.jim.wx.awesomebasicpractice.recyclerview.layoutmanager;

import android.content.Context;
import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wx on 2017/12/19.
 * <p>
 * 先学习别人做个
 * <p>
 * http://www.jianshu.com/p/08d998d047d8
 * <p>
 * 二级缓存：
 * Scrap这是detach的view，无需重新绑定数据
 * Recycle这是remove掉的view，需要重新绑定数据，或者创建
 */

public class ABCLayoutManager extends RecyclerView.LayoutManager {
    private static final String TAG = "ABCLayoutManager";

    public ABCLayoutManager(Context context) {

    }

    //只有这一个抽象方法
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * Step 1
     * 光重写这个方法，会直接layout全部view，会实例化数据源个数个view，没有回收机制
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //detach all view, and put in Scrap(First level cache)
        detachAndScrapAttachedViews(recycler);

        //定以竖直方向的偏移量
        int offsetY = 0;
        for (int i = 0; i < getItemCount(); i++) {
            //从缓存里面取出来一个view
            View view = recycler.getViewForPosition(i);
            //添加view
            addView(view);
            //测量view
            measureChildWithMargins(view, 0, 0);
            //获取宽高
            int width = getDecoratedMeasuredWidth(view);
            int height = getDecoratedMeasuredHeight(view);

            totalHeight += height;

            //保存布局信息
            saveFrames(i, 0, offsetY, width, height + offsetY);
            //标记状态
            hasAttachedItems.put(i, false);

            //布局
//            layoutDecorated(view, 0, offsetY, width, offsetY + height);
            offsetY += height;

            Log.d(TAG, "onLayoutChildren: " + i);

        }
        recycleAndFillItems(recycler, state);
    }

    /**
     * Step 2
     * 现在就是个ListView？，可以垂直滑动
     */
    @Override
    public boolean canScrollVertically() {
        return true;
    }

    /**
     * Step 3
     * 光上面还是不能滑动
     */
    private int verticalScrollOffset = 0;
    private int totalHeight = 0;

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        detachAndScrapAttachedViews(recycler);

        //物理上可以移动的距离
        int travel = dy;

        //这两个判断是顶部和底部的边界判断
        //如果会滑出顶部屏幕，那么就只能滑剩下的高度了
        if (verticalScrollOffset + dy < 0) {
            travel = -verticalScrollOffset;
            //要滑出底部屏幕了，只滑剩下的高度，竖直方向上能滑动的距离其实是，所有item的高度减去recyclerview自己的高度
        } else if (verticalScrollOffset + dy > totalHeight - getHeight()) {
            travel = totalHeight - verticalScrollOffset - getHeight();
        }


        //记录所处的位置
        verticalScrollOffset += travel;
        //平移内部view
        offsetChildrenVertical(-travel);

        recycleAndFillItems(recycler, state);
        return travel;
    }

    /**
     * Step 4
     * View的重复利用
     */
    //存储所有item的布局信息
    private SparseArray<Rect> allItemFrames = new SparseArray<>();
    //标记该item是否已经出现在屏幕上，而且没有被回收
    private SparseBooleanArray hasAttachedItems = new SparseBooleanArray();


    private void saveFrames(int pos, int l, int t, int r, int b) {
        Rect rect = allItemFrames.get(pos);
        if (rect == null) {
            rect = new Rect();
        }
        rect.set(l, t, r, b);
        allItemFrames.put(pos, rect);
    }


    private void recycleAndFillItems(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            return;
        }
        //定义要RecyclerView要显示的区域, 一般来说都是显示一整个屏幕的区域
        Rect displayFrame = new Rect(0, verticalScrollOffset, getWidth(), verticalScrollOffset + getHeight());

        //回收不在区域的item, 要用getChildCount
        Rect itemFrame = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View item = getChildAt(i);
            itemFrame.left = getDecoratedLeft(item);
            itemFrame.top = getDecoratedTop(item);
            itemFrame.right = getDecoratedRight(item);
            itemFrame.bottom = getDecoratedBottom(item);
            if (!Rect.intersects(itemFrame, displayFrame)) {
                //回收view
                removeAndRecycleView(item, recycler);
            }
        }

        //利用已经回收的item重新显示, 要用getItemCount
        for (int i = 0; i < getItemCount(); i++) {
            if (Rect.intersects(displayFrame, allItemFrames.get(i))) {
                View scrap = recycler.getViewForPosition(i);
                measureChildWithMargins(scrap, 0, 0);
                addView(scrap);

                Rect frame = allItemFrames.get(i);
                layoutDecorated(scrap, frame.left, frame.top - verticalScrollOffset, frame.right, frame.bottom - verticalScrollOffset);
            }
        }
    }
}

/**
 * 总结：
 * 1、应该是有问题的，假如有100条数据，还是会先创建100个view，onCreateViewHolder会回调100次
 * 2、只是让attach在RecyclerView中的child减少
 *
 * 优化思路：
 * 1、一开始只测量和实例化 规定显示区域displayFrame的itemview
 * 2、在scroll的过程中有需求就进行增加
 */
