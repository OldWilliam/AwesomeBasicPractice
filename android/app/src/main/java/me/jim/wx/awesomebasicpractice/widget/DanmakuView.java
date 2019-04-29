package me.jim.wx.awesomebasicpractice.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * Date: 2019/3/20
 * Name: wx
 * Description:
 */
public class DanmakuView extends HorizontalScrollView {

    public DanmakuView(@NonNull Context context) {
        super(context);
        init();
    }

    public DanmakuView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DanmakuView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressWarnings("unused")
    public DanmakuView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Thread.dumpStack();
    }

    private void init() {


        LinearLayout mContainer = new LinearLayout(getContext());
        mContainer.setOrientation(LinearLayout.HORIZONTAL);
        addView(mContainer);

        for (int i = 0; i < 6; i++) {
            TextView textView = createTextView(i);
            mContainer.addView(textView);
        }

        createAnimator(mContainer);
    }

    private void createAnimator(View textView) {
        textView.measure(0, 0);
        ValueAnimator animator = ValueAnimator.ofFloat(-textView.getMeasuredWidth(), getWidth());
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10 * 1000);
        animator.addUpdateListener(animation -> textView.setTranslationX((Float) animation.getAnimatedValue()));
        animator.start();
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    private TextView createTextView(int i) {
        final TextView textView = new TextView(getContext());
        textView.setText(String.format(Locale.US, "我是%d条弹幕", i));
        textView.setSingleLine();
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
