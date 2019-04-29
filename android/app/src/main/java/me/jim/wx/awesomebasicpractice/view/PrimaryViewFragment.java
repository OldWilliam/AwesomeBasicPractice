package me.jim.wx.awesomebasicpractice.view;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import me.jim.wx.awesomebasicpractice.view.recyclerview.CenterLayoutManager;
import me.jim.wx.awesomebasicpractice.view.recyclerview.LimitLinearSnapHelper;

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

        initWheel(view);
    }

    private void initWheel(final View view) {
        RecyclerView wheel = view.findViewById(R.id.wheel);
        wheel.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        wheel.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new WheelViewHolder(View.inflate(getContext(), R.layout.item_wheel, null));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                ((WheelViewHolder) viewHolder).setName(i + "");
                if (i == 0 || i == 1 || i == getItemCount() - 1 || i == getItemCount() - 2) {
                    viewHolder.itemView.setVisibility(View.INVISIBLE);
                } else {
                    viewHolder.itemView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public int getItemCount() {
                return 25;
            }
        });

        wheel.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = 30;
                outRect.right = 30;
            }
        });

        LinearSnapHelper mSnapHelper = new LimitLinearSnapHelper();
        mSnapHelper.attachToRecyclerView(wheel);

        wheel.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int centerIndex = recyclerView.getChildCount() / 2;
                View center = recyclerView.getChildAt(centerIndex);
                View left = recyclerView.getChildAt(centerIndex - 1);
                View right = recyclerView.getChildAt(centerIndex + 1);

                center.setScaleX(1.5f);
                center.setScaleY(1.5f);
                center.setTranslationY(0);

                left.setScaleX(1);
                left.setScaleY(1);
                left.setTranslationY(30);

                right.setScaleX(1);
                right.setScaleY(1);
                right.setTranslationY(60);
            }
        });
    }

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

    private static class WheelViewHolder extends RecyclerView.ViewHolder {
        public WheelViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setName(String name) {
            ((TextView) itemView.findViewById(R.id.tv_name)).setText(name);
        }
    }

}
