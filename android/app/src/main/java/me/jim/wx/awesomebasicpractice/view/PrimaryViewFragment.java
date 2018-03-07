package me.jim.wx.awesomebasicpractice.view;


import android.animation.ObjectAnimator;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.view.primary.FlowLayout;

/**
 * 自定义View练习
 */
public class PrimaryViewFragment extends Fragment {

    public static Fragment newInstance() {
        return new PrimaryViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init(view);
    }

    private void init(View view) {
        /*流式布局*/
        initFlowLayout(view);
        /*shape 实现圆形进度条*/
        initProgressView(view);
    }

    private void initProgressView(View view) {
        ImageView progress = view.findViewById(R.id.progress);
        Animation rotateAnim = AnimationUtils.loadAnimation(getContext(), R.anim.roate);
        progress.startAnimation(rotateAnim);
    }

    private void initFlowLayout(View view) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        FlowLayout layout = view.findViewById(R.id.flow_layout);
        for (int i = 0; i < 19; i++) {
            layout.addView(inflater.inflate(R.layout.item_flow, null), 0);
        }
        layout.setArrow(true);
    }

}
