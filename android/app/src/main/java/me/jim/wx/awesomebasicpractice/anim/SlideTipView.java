package me.jim.wx.awesomebasicpractice.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import me.jim.wx.awesomebasicpractice.R;

/**
 * Created by wx on 2018/4/16.
 *
 * 侧边滑动隐藏
 */

public class SlideTipView extends LinearLayout {

    private View ivIcon;
    private View llContent;

    private ObjectAnimator animIcon, animContent;
    private AnimatorSet animatorSet;

    public SlideTipView(Context context) {
        super(context);
        initView();
    }

    public SlideTipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_slide_tip, this);
        ivIcon = findViewById(R.id.iv_icon);
        llContent = findViewById(R.id.ll_content);
        initAnim();
        llContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                animatorSet.start();
            }
        });
        ivIcon.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                ivIcon.setRotation(0);
                llContent.setRotationY(0);
                animatorSet.start();
            }
        });
    }

    private void initAnim() {
        ivIcon.setPivotX(0);
        ivIcon.setPivotY(72);
        animIcon = ObjectAnimator.ofFloat(ivIcon, "rotation", 0, 120);
        animIcon.setInterpolator(new OvershootInterpolator());
        animIcon.setDuration(300);

        llContent.measure(0, 0);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1.0f, 1.0f);
        PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("translationX", 0, -llContent.getMeasuredWidth());
        animContent = ObjectAnimator.ofPropertyValuesHolder(llContent, alpha, translationX);
        animContent.setInterpolator(new LinearInterpolator());
        animContent.setDuration(200);

        animatorSet = new AnimatorSet();
        animatorSet.playTogether(animContent, animIcon);
    }
}
