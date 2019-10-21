package me.jim.wx.awesomebasicpractice.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.jim.wx.awesomebasicpractice.R;

/**
 * Date: 2019-10-21
 * Name: weixin
 * Description:  左右轮播选择
 */
public class Gallery extends FrameLayout {
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
        final RecyclerView recyclerView = new RecyclerView(getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager.scrollToPosition(Integer.MAX_VALUE / 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new GalleryAdapter());

        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), LinearLayout.HORIZONTAL);
        Drawable drawable = getContext().getResources().getDrawable(R.drawable.simple_decor);
        decoration.setDrawable(drawable);
        recyclerView.addItemDecoration(decoration);

        final LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        View snapView = snapHelper.findSnapView(linearLayoutManager);
        int[] snapDistance = snapHelper.calculateDistanceToFinalSnap(linearLayoutManager, snapView);
        recyclerView.offsetChildrenHorizontal(snapDistance[0]);

        addView(recyclerView);
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
            ((TextView) holder.itemView).setText(String.valueOf(position % 10));
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
        }
    }

    private class GalleryViewHolder extends RecyclerView.ViewHolder {
        GalleryViewHolder(View itemView) {
            super(itemView);
        }
    }

}
