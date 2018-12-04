package me.jim.wx.awesomebasicpractice.view;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import java.io.IOException;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.graphic.QuestionMarkDrawable;
import me.jim.wx.awesomebasicpractice.other.aspect.VisitorAnnotation;
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
        /*TextureView使用*/
        initTextureView(view);
        /*RadioButton*/
        initRadioButton(view);
        /*扩展动画View*/
        initExpandAnimView(view);
        /*设置drawable*/
        initDrawableView(view);
        /*hook 测试*/
        initHookView(view);
        /*svga*/
        initSVGA(view);
    }

    private void initHookView(View view) {
        TextView tvHook = view.findViewById(R.id.tv_hook);
        tvHook.setOnClickListener(new View.OnClickListener() {
            @Override
            @VisitorAnnotation
            public void onClick(View v) {
                Toast.makeText(getContext(), "HAHA", Toast.LENGTH_SHORT).show();
            }
        });
//        HookHelper.hookClickListener(tvHook);
    }

    private void initDrawableView(View view) {
        QuestionMarkDrawable drawable = new QuestionMarkDrawable();
        view.findViewById(R.id.drawable_view).setBackgroundDrawable(drawable);
    }

    private void initExpandAnimView(View view) {
        View target = view.findViewById(R.id.expand_anim_view);
        target.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 540));
    }

    private void initRadioButton(View view) {
        RadioButton button = view.findViewById(R.id.radiobutton);
        button.setButtonDrawable(null);
    }

    private void initTextureView(View view) {
        TextureView textureView = view.findViewById(R.id.textureview);
        final Camera camera = Camera.open();
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                try {
                    camera.setPreviewTexture(surface);
                    camera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                camera.stopPreview();
                camera.release();
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
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
    }

    private void initSVGA(final View parent) {
        parent.findViewById(R.id.btn_svga).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout animContainer = parent.findViewById(R.id.container_primary);

                final SVGAImageView svgaImageView = new SVGAImageView(getContext());
                svgaImageView.setLoops(1);
                svgaImageView.setCallback(new SVGACallback() {
                    @Override
                    public void onPause() {
                        Log.d("PrimaryViewFragment", "onPause: ");
                    }

                    @Override
                    public void onFinished() {
                        Log.d("PrimaryViewFragment", "onFinished: ");
                    }

                    @Override
                    public void onRepeat() {
                        Log.d("PrimaryViewFragment", "onRepeat: ");
                    }

                    @Override
                    public void onStep(int i, double v) {
                        Log.d("PrimaryViewFragment", "onStep: ");
                    }
                });

                final ValueAnimator animator = ValueAnimator.ofInt(1).setDuration(3000);
                animator.setInterpolator(new LinearInterpolator());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        svgaImageView.stepToPercentage(animation.getAnimatedFraction(), true);
                    }
                });
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        svgaImageView.stopAnimation();
                        animContainer.removeView(svgaImageView);
                    }
                });


                SVGAParser svgaParser = new SVGAParser(getContext());
                svgaParser.parse("xiexie.svga", new SVGAParser.ParseCompletion() {
                    @Override
                    public void onComplete(SVGAVideoEntity svgaVideoEntity) {

                        animContainer.addView(svgaImageView, 0, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));

                        SVGADrawable svgaDrawable = new SVGADrawable(svgaVideoEntity);
                        svgaImageView.setImageDrawable(svgaDrawable);

                        animator.start();
                    }

                    @Override
                    public void onError() {
                        Log.d("PrimaryViewFragment", "onError: ");
                    }
                });
            }
        });
    }

}
