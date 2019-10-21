package me.jim.wx.awesomebasicpractice.view;


import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.CountDownLatch;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.camera.CameraPreview;
import me.jim.wx.awesomebasicpractice.graphic.QuestionMarkDrawable;
import me.jim.wx.awesomebasicpractice.opengl.SurfaceRender;
import me.jim.wx.awesomebasicpractice.other.hook.HookHelper;
import me.jim.wx.awesomebasicpractice.view.primary.FlowLayout;
import me.jim.wx.fragmentannotation.AttachFragment;

/**
 * 自定义View练习
 */
@SuppressWarnings("unused")
@AttachFragment("初级View")
public class PrimaryViewFragment extends Fragment {

    private static final int REQUEST_CODE_CAMERA = 100;

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
        /*GLSurfaceView*/
        initGlView(view);
        /*抛物线动画*/
//        initParabolaAnim(view);
        /*自由落体小球*/
        initFreeFallBall(view);
    }

    private void initFreeFallBall(final View view) {
        SurfaceView mSurfaceView = view.findViewById(R.id.surfaceView);
        final SurfaceHolder holder = mSurfaceView.getHolder();
        final CountDownLatch latch = new CountDownLatch(1);

        Thread thread = new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Looper.prepare();
            final Paint paint = new Paint();
            paint.setColor(Color.YELLOW);
            ValueAnimator animator = ValueAnimator.ofInt(0, 1200);
            final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
            animator.setDuration(3 * 1000);
            animator.setInterpolator(new AccelerateInterpolator());

            animator.addUpdateListener(animation -> {
                //这里的确是在子线程绘制的，AnimationHandle是ThreadLocal的，Choreographer也是ThreadLocal的，Choreographer初始化会传一个Looper，用的是当前线程的Looper
                //所以会回到当前线程执行
                if (!Thread.interrupted()) {
                    Canvas canvas = holder.lockCanvas();
                    if (canvas != null) {
                        canvas.drawColor((Integer) argbEvaluator.evaluate(animation.getAnimatedFraction(), Color.WHITE, Color.GREEN));
                        canvas.translate(0, (Integer) animation.getAnimatedValue());
                        canvas.drawCircle(100, 40, 40, paint);
                        holder.unlockCanvasAndPost(canvas);
                    }
                }
            });
            animator.start();
            Looper.loop();
        });
        thread.start();

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(final SurfaceHolder h) {
                Log.e(TAG, "surfaceCreated: " + Thread.currentThread());
                latch.countDown();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.e(TAG, "surfaceChanged: " + Thread.currentThread());
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.e(TAG, "surfaceDestroyed: " + Thread.currentThread());
                thread.interrupt();
            }
        });
    }

    @SuppressWarnings("unused")
    private void initParabolaAnim(View view) {

        final TextView textView = new TextView(getContext());
        textView.setText("鑫");

        final FrameLayout layout = new FrameLayout(getContext());
        layout.setBackgroundColor(Color.GREEN);
        layout.addView(textView, 50, 50);

        ((ViewGroup) ((ViewGroup) view).getChildAt(0)).addView(layout, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 800));

        ValueAnimator yAnim = ValueAnimator.ofInt(0, 600);
        yAnim.setDuration(10 * 1000);
        yAnim.setInterpolator(new AccelerateInterpolator());
        yAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.topMargin = (int) animation.getAnimatedValue();
                layoutParams.leftMargin = (int) (animation.getCurrentPlayTime() / 1000.0f * 80);
                textView.requestLayout();
            }
        });
        yAnim.start();
    }

    private void initGlView(View view) {
        final GLSurfaceView glView = view.findViewById(R.id.gl_view);
        glView.setRenderer(new SurfaceRender());
    }

    private static final String TAG = "PrimaryViewFragment";

    private void initHookView(View view) {
        TextView tvHook = view.findViewById(R.id.tv_hook);
        tvHook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "HAHA", Toast.LENGTH_SHORT).show();
            }
        });
        HookHelper.hookClickListener(tvHook);
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
        } else {
            realInitTextureView();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //success granted
                realInitTextureView();
            } else {
                //fail denied
            }
        }
    }

    private void realInitTextureView() {
        TextureView textureView = getView().findViewById(R.id.textureview);

        CameraPreview cameraPreview = new CameraPreview(getContext(), textureView, (View) textureView.getParent());
        cameraPreview.startCamera();

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

}
