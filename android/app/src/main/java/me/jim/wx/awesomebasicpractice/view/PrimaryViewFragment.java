package me.jim.wx.awesomebasicpractice.view;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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

import java.io.IOException;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.graphic.QuestionMarkDrawable;
import me.jim.wx.awesomebasicpractice.other.hook.HookHelper;
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
    }

    private void initHookView(View view) {
        final TextView tvHook = view.findViewById(R.id.tv_hook);
        tvHook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "HAHA", Toast.LENGTH_SHORT).show();

                tvHook.setText("Xposed");
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

}
