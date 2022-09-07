package me.jim.wx.awesomebasicpractice.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Date: 2019-10-21
 * Name: weixin
 * Description:  左右轮播选择器
 */
public class Gallery extends FrameLayout {

    private RecyclerView recyclerView;

    public Gallery(Context context) {
        super(context);
        init();
    }

    public Gallery(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Gallery(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressWarnings("unused")
    public Gallery(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        recyclerView = new RecyclerView(getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.scrollToPosition(Integer.MAX_VALUE / 2);
        recyclerView.setLayoutManager(layoutManager);

        GalleryAdapter mAdapter = new GalleryAdapter();
        recyclerView.setAdapter(mAdapter);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = 30;
                outRect.right = 30;
            }
        });

        addView(recyclerView);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final LinearSnapHelper snapHelper = new LinearSnapHelper();
                snapHelper.attachToRecyclerView(recyclerView);
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                computeScale();
            }
        });

        recyclerView.setMinimumHeight(180 * 3);
    }

    private void computeScale() {

        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
            float percent = getPercent(holder.itemView);
            float factor = 0.6f;
            if (percent < 1) {
                holder.itemView.setScaleX(1 - percent * (1 - factor));
                holder.itemView.setScaleY(1 - percent * (1 - factor));
            } else {
                holder.itemView.setScaleY(factor);
                holder.itemView.setScaleX(factor);
            }
        }
    }

    private float getPercent(View itemView) {
        float parentCenter = getWidth() / 2;

        float itemCenter = itemView.getLeft() + itemView.getWidth() / 2;

        float du = Math.abs(parentCenter - itemCenter);
        return du / itemView.getWidth();
    }

    private class GalleryAdapter extends RecyclerView.Adapter {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            TextView textView = new TextView(parent.getContext());
            textView.setBackgroundColor(Color.RED);
            textView.setText("HelloWorld");
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(new ViewGroup.LayoutParams(160 * 3, 160 * 3));
            return new GalleryViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            int index = position % 10;
            ((TextView) holder.itemView).setText(String.valueOf(index));
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
        }
    }

    private static class GalleryViewHolder extends RecyclerView.ViewHolder {
        GalleryViewHolder(View itemView) {
            super(itemView);
        }
    }
}
