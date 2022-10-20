package me.jim.wx.awesomebasicpractice.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.graphic.ExpandDrawable;

/**
 * Created by wx on 2018/3/21.
 */

public class ExpandAnimView extends RelativeLayout implements View.OnClickListener {
    private static final String TAG = "ExpandAnimView";

    private View host, normal, game, video, sound;
    Drawable mBackground;
    private List<Animator> mAnimators;

    public ExpandAnimView(Context context) {
        super(context);
        init();
    }

    public ExpandAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initView();
        initAnim();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.anim_escape_view, this);
        host = findViewById(R.id.host);
        normal = findViewById(R.id.normal);
        game = findViewById(R.id.game);
        video = findViewById(R.id.video);
        sound = findViewById(R.id.sound);
        setOnClickListener(this);
    }

    private void initAnim() {
        mBackground = new ExpandDrawable();
        setBackground(mBackground);
        mAnimators = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            mAnimators.add(createAnim2(getChildAt(i), i, getChildCount()));
        }
    }

    /**
     * 使用PropertyValuesHolder
     */
    private Animator createAnim2(View child, int index, int count) {
        int item = 180 / (count + 1);
        int radius = getResources().getDisplayMetrics().widthPixels / 2;
        int angle = (index + 1) * item;
        float tranX = (float) (-Math.cos(Math.toRadians(angle)) * radius);
        float tranY = (float) (-Math.sin(Math.toRadians(angle)) * radius);

        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("translationX", 0, tranX);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("translationY", 0, tranY);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(child, x, y);
        animator.setDuration(1200);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        return animator;
    }

    /**
     * 使用ValueAnimator
     */
    private Animator createAnim(final View child, final int index, int count) {
        int item = 180 / (count + 1);
        int radius = getResources().getDisplayMetrics().widthPixels / 2;
        int angle = (index + 1) * item;
        double tranX = -Math.cos(Math.toRadians(angle)) * radius;
        double tranY = -Math.sin(Math.toRadians(angle)) * radius;


        ValueAnimator animX = ValueAnimator.ofFloat(0f, (float) tranX);
        animX.setDuration(1200);
        animX.setRepeatMode(ValueAnimator.REVERSE);
        animX.setRepeatCount(ValueAnimator.INFINITE);
        animX.setInterpolator(new DecelerateInterpolator());
        animX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                child.setTranslationX((Float) animation.getAnimatedValue());
                Log.d(TAG, "onAnimationUpdateX: " + index + " :: " + animation.getAnimatedValue());
            }
        });

        ValueAnimator animY = ValueAnimator.ofFloat(0f, (float) tranY);
        animY.setDuration(1200);
        animY.setRepeatMode(ValueAnimator.REVERSE);
        animY.setRepeatCount(ValueAnimator.INFINITE);
        animY.setInterpolator(new DecelerateInterpolator());
        animY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                child.setTranslationY((Float) animation.getAnimatedValue());
                Log.d(TAG, "onAnimationUpdateY: " + index + " :: " + animation.getAnimatedValue());
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animX, animY);
        return animatorSet;
    }

    @Override
    public void onClick(View v) {
        ((ExpandDrawable) mBackground).start();
        for (int i = 0; i < mAnimators.size(); i++) {
            mAnimators.get(i).start();
        }
    }
}
