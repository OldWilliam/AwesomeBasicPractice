package me.jim.wx.awesomebasicpractice.view;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.util.Log;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.graphic.QuestionMarkDrawable;
import me.jim.wx.awesomebasicpractice.other.aspect.VisitorAnnotation;
import me.jim.wx.awesomebasicpractice.view.primary.FlowLayout;

/**
 * 自定义View练习
 */
public class PrimaryViewFragment extends Fragment {

    private static final int REQUEST_CODE_CAMERA = 100;

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
        initTextureView();
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

    private void initTextureView() {

        Activity context = getActivity();
        if (context == null) {
            return;
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.CAMERA)) {
//
//            }else {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
//            }
        }else {
            realInitTextureView();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //success granted
                realInitTextureView();
            }else {
                //fail denied
            }
        }
    }

    private void realInitTextureView() {
        TextureView textureView = getView().findViewById(R.id.textureview);
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
                svgaImageView.setLoops(Integer.MAX_VALUE);
                svgaImageView.setCallback(new SVGACallback() {
                    @Override
                    public void onPause() {
                        Log.d("PrimaryViewFragment", "onPause: ");
                    }

                    @Override
                    public void onFinished() {
                        Log.d("PrimaryViewFragment", "onFinished: ");
                        animContainer.removeView(svgaImageView);
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

//                final ValueAnimator animator = ValueAnimator.ofInt(1).setDuration(3000);
//                animator.setInterpolator(new LinearInterpolator());
//                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        svgaImageView.stepToPercentage(animation.getAnimatedFraction(), true);
//                    }
//                });
//                animator.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        svgaImageView.stopAnimation();
//                        animContainer.removeView(svgaImageView);
//                    }
//                });

                boolean isMvp = new Random().nextBoolean();

                SVGAParser svgaParser = new SVGAParser(getContext());
                URL url = null;
                try {
                    url = new URL("http://m4a.inke.cn/MTU0NTgxNjA3NTQ5MiM2MDUjbTRh.m4a");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                svgaParser.parse(url, new SVGAParser.ParseCompletion() {
                    @Override
                    public void onComplete(final SVGAVideoEntity svgaVideoEntity) {

                        int width = (int) svgaVideoEntity.getVideoSize().getWidth();
                        int height = (int) svgaVideoEntity.getVideoSize().getHeight();

                        animContainer.addView(svgaImageView,0, new LinearLayout.LayoutParams(width,height));

                        DateHornerModel model = new DateHornerModel();
                        model.nick = "宇智波佐助";
                        model.ptr = "http://img.ikstatic.cn/MTU0MTA1NjY4NjgwMyMxMSNqcGc=.jpg";
//                        model.res = "http://img.ikstatic.cn/MTU0NTUzOTA3NjA0NSM1MTIjanBn.jpg";
                        model.res = "http://img.ikstatic.cn/MTU0NTczMDQ5MTMyMiM4NDgjanBn.jpg";

                        final SVGADynamicEntity svgaDynamicEntity = getSvgaDynamicEntity(model, false);

                        Fresco.initialize(getContext());
                        Fresco.getImagePipeline().fetchDecodedImage(ImageRequest.fromUri(model.res), getContext()).subscribe(new BaseBitmapDataSubscriber() {
                            @Override
                            protected void onNewResultImpl(@javax.annotation.Nullable Bitmap bitmap) {
                                svgaDynamicEntity.setDynamicImage(bitmap, "hat01");
                                SVGADrawable svgaDrawable = new SVGADrawable(svgaVideoEntity, svgaDynamicEntity);
                                svgaImageView.setImageDrawable(svgaDrawable);
                                svgaImageView.startAnimation();
                            }

                            @Override
                            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                                Log.d("PrimaryViewFragment", "onFailureImpl: ");
                            }
                        }, CallerThreadExecutor.getInstance());
                    }

                    @Override
                    public void onError() {
                        Log.d("PrimaryViewFragment", "onError: ");
                    }
                });
            }
        });
    }

    @NonNull
    private SVGADynamicEntity getSvgaDynamicEntity(DateHornerModel model, boolean isMvp) {

        final SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();

        dynamicEntity.setDynamicImage(model.ptr, "photo01");

//        if (isMvp) {
//            dynamicEntity.setDynamicImage(model.res, "mvpk");
//        } else {
//            dynamicEntity.setDynamicImage(model.res, "hat01");
//        }

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(26);
        textPaint.setARGB(255, 255, 255, 255);
        textPaint.setFakeBoldText(true);
        dynamicEntity.setDynamicText(model.nick, textPaint, "name01");

        TextPaint textPaint2 = new TextPaint();
        textPaint2.setTextSize(40);
        textPaint2.setARGB(255, 255, 255, 255);
        textPaint2.setFakeBoldText(true);
        if (isMvp) {
            dynamicEntity.setDynamicText("抢到MVP", textPaint2, "name02");
        } else {
            dynamicEntity.setDynamicText("抢到帽子", textPaint2, "name02");
        }
        return dynamicEntity;
    }

    private class DateHornerModel {
        public String res;
        public String nick;
        public String ptr;
    }
}
