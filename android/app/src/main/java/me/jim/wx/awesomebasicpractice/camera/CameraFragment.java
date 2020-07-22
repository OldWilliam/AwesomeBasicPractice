package me.jim.wx.awesomebasicpractice.camera;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.graphic.HumanFaceFinderDrawable;
import me.jim.wx.fragmentannotation.AttachFragment;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("deprecation")
@AttachFragment("相机")
public class CameraFragment extends Fragment implements SurfaceHolder.Callback, View.OnTouchListener, View.OnLongClickListener {

    private static final String TAG = "CameraFragment";

    private Camera mCamera;
    private SurfaceHolder mHolder;
    private SurfaceView surfaceView;
    private ImageView preview;
    private ImageView take;

    private int mCameraCount;
    private int mCurrentCamId = Camera.CameraInfo.CAMERA_FACING_BACK;

    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        surfaceView = view.findViewById(R.id.surfaceView);
        mHolder = surfaceView.getHolder();
        mHolder.addCallback(this);

        surfaceView.setOnTouchListener(this);

        initListener(view);

        preview = view.findViewById(R.id.preview);

        preview.setOnClickListener(v -> preview.setVisibility(View.GONE));

        view.findViewById(R.id.iv_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.stopPreview();
                mCamera.release();

                try {
                    prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mCamera.startPreview();
            }
        });
    }

    private void initListener(@NonNull View view) {
        take = view.findViewById(R.id.take);
        take.setOnLongClickListener(this);

        ImageView cover = view.findViewById(R.id.cover);
        cover.setBackground(new HumanFaceFinderDrawable(view.getContext()));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mHolder = holder;
        try {
            prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
    }

    private void prepare() throws IOException {
        mCameraCount = Camera.getNumberOfCameras();
        mCurrentCamId = (mCurrentCamId + 1) % mCameraCount;
        mCamera = Camera.open(mCurrentCamId);
        mCamera.setPreviewDisplay(mHolder);

        Log.d(TAG, surfaceView.getWidth() + " : " + surfaceView.getHeight());

        int rotation = getDisplayOrientation();
        mCamera.setDisplayOrientation(rotation);

        Camera.Parameters parameters = mCamera.getParameters();
        setPreviewSize(parameters);
        setPictureSize(parameters);
        parameters.setRotation(isFrontCamera() ? rotation + 180 : rotation);
        mCamera.setParameters(parameters);
    }

    /**
     * 设置保存图片的尺寸
     */
    private void setPictureSize(Camera.Parameters mParameters) {
        List<Camera.Size> localSizes = mParameters.getSupportedPictureSizes();
        Camera.Size biggestSize = null;
        Camera.Size fitSize = null;// 优先选预览界面的尺寸
        Camera.Size previewSize = mParameters.getPreviewSize();//获取预览界面尺寸
        float previewSizeScale = 0;
        if (previewSize != null) {
            previewSizeScale = previewSize.width / (float) previewSize.height;
        }

        if (localSizes != null) {
            int cameraSizeLength = localSizes.size();
            for (int n = 0; n < cameraSizeLength; n++) {
                Camera.Size size = localSizes.get(n);
                if (biggestSize == null) {
                    biggestSize = size;
                } else if (size.width >= biggestSize.width && size.height >= biggestSize.height) {
                    biggestSize = size;
                }

                // 选出与预览界面等比的最高分辨率
                if (previewSizeScale > 0
                        && size.width >= previewSize.width && size.height >= previewSize.height) {
                    float sizeScale = size.width / (float) size.height;
                    if (sizeScale == previewSizeScale) {
                        if (fitSize == null) {
                            fitSize = size;
                        } else if (size.width >= fitSize.width && size.height >= fitSize.height) {
                            fitSize = size;
                        }
                    }
                }
            }

            // 如果没有选出fitSize, 那么最大的Size就是FitSize
            if (fitSize == null) {
                fitSize = biggestSize;
            }
            mParameters.setPictureSize(fitSize.width, fitSize.height);
        }
    }

    /**
     * 预览界面尺寸
     */
    private void setPreviewSize(Camera.Parameters mParameters) {
        //获取系统支持预览大小
        List<Camera.Size> localSizes = mParameters.getSupportedPreviewSizes();
        Camera.Size biggestSize = null;//最大分辨率
        Camera.Size fitSize = null;// 优先选屏幕分辨率
        Camera.Size targetSize = null;// 没有屏幕分辨率就取跟屏幕分辨率相近(大)的size
        Camera.Size targetSiz2 = null;// 没有屏幕分辨率就取跟屏幕分辨率相近(小)的size
        if (localSizes != null) {
            int cameraSizeLength = localSizes.size();
            for (int n = 0; n < cameraSizeLength; n++) {
                Camera.Size size = localSizes.get(n);
                Log.d("sssd-系统支持的尺寸:", size.width + "*" + size.height);
                if (biggestSize == null ||
                        (size.width >= biggestSize.width && size.height >= biggestSize.height)) {
                    biggestSize = size;
                }

                //如果支持的比例都等于所获取到的宽高
                int screenWidth = surfaceView.getWidth();
                int screenHeight = surfaceView.getHeight();
                if (size.width == screenHeight
                        && size.height == screenWidth) {
                    fitSize = size;
                    //如果任一宽或者高等于所支持的尺寸
                } else {
                    if (size.width == screenHeight
                            || size.height == screenWidth) {
                        if (targetSize == null) {
                            targetSize = size;
                            //如果上面条件都不成立 如果任一宽高小于所支持的尺寸
                        } else if (size.width < screenHeight
                                || size.height < screenWidth) {
                            targetSiz2 = size;
                        }
                    }
                }
            }

            if (fitSize == null) {
                fitSize = targetSize;
            }

            if (fitSize == null) {
                fitSize = targetSiz2;
            }

            if (fitSize == null) {
                fitSize = biggestSize;
            }
            Log.d("sssd-最佳预览尺寸:", fitSize.width + "*" + fitSize.height);
            mParameters.setPreviewSize(fitSize.width, fitSize.height);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        int rotation = getDisplayOrientation();
        mCamera.setDisplayOrientation(rotation);

        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setRotation(isFrontCamera() ? rotation + 180 : rotation);
        mCamera.setParameters(parameters);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mHolder.removeCallback(this);
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    public int getDisplayOrientation() {
        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        android.hardware.Camera.CameraInfo camInfo =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, camInfo);

        return (camInfo.orientation - degrees + 360) % 360;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getPointerCount() == 1) {
            try {//有的摄像头不支持聚焦
                handleFocus(event, mCamera);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean isFrontCamera() {
        return mCurrentCamId == Camera.CameraInfo.CAMERA_FACING_FRONT;
    }

    private void handleFocus(MotionEvent event, Camera camera) {
        int viewWidth = getWidth();
        int viewHeight = getHeight();
        Rect focusRect = calculateTapArea(event.getX(), event.getY(), 1f, viewWidth, viewHeight);

        camera.cancelAutoFocus();
        Camera.Parameters params = camera.getParameters();
        if (params.getMaxNumFocusAreas() > 0) {
            List<Camera.Area> focusAreas = new ArrayList<>();
            focusAreas.add(new Camera.Area(focusRect, 800));
            params.setFocusAreas(focusAreas);
        } else {
            Log.i(TAG, "focus areas not supported");
        }
        final String currentFocusMode = params.getFocusMode();
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
        camera.setParameters(params);

        camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                Camera.Parameters params = camera.getParameters();
                params.setFocusMode(currentFocusMode);
                camera.setParameters(params);
            }
        });
    }

    private int getHeight() {
        return surfaceView.getHeight();
    }

    private int getWidth() {
        return surfaceView.getWidth();
    }

    private static Rect calculateTapArea(float x, float y, float coefficient, int width, int height) {
        float focusAreaSize = 300;
        int areaSize = Float.valueOf(focusAreaSize * coefficient).intValue();
        int centerX = (int) (x / width * 2000 - 1000);
        int centerY = (int) (y / height * 2000 - 1000);

        int halfAreaSize = areaSize / 2;
        RectF rectF = new RectF(clamp(centerX - halfAreaSize, -1000, 1000)
                , clamp(centerY - halfAreaSize, -1000, 1000)
                , clamp(centerX + halfAreaSize, -1000, 1000)
                , clamp(centerY + halfAreaSize, -1000, 1000));
        return new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
    }

    private static int clamp(int x, int min, int max) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
    }

    @Override
    public boolean onLongClick(View v) {
        takePicture();
        return true;
    }

    private void takePicture() {
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Bitmap bitmap;
                        if (isFrontCamera()) {
                            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
                            if (pictureFile == null) {
                                Log.d(TAG, "Error creating media file, check storage permissions");
                                return;
                            }
                            try {
                                FileOutputStream fos = new FileOutputStream(pictureFile);
                                fos.write(data);
                                fos.close();

                            } catch (FileNotFoundException e) {
                                Log.d(TAG, "File not found: " + e.getMessage());
                            } catch (IOException e) {
                                Log.d(TAG, "Error accessing file: " + e.getMessage());
                            }

                            preview.post(new Runnable() {
                                @Override
                                public void run() {
                                    camera.startPreview();
                                    preview.setVisibility(View.VISIBLE);
                                    Glide.with(getContext()).load(getOutputMediaFileUri()).into(preview);
                                }
                            });

                        } else {
                            bitmap = getPreview(data);
                            preview.post(new Runnable() {
                                @Override
                                public void run() {
                                    camera.startPreview();
                                    preview.setVisibility(View.VISIBLE);
                                    preview.setImageBitmap(bitmap);
                                }
                            });
                            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
                            FileOutputStream fos = null;
                            try {
                                fos = new FileOutputStream(pictureFile);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                fos.flush();
                            } catch (FileNotFoundException e) {
                                Log.d(TAG, "File not found: " + e.getMessage());
                            } catch (IOException e) {
                                Log.d(TAG, "Error accessing file: " + e.getMessage());
                            } finally {
                                try {
                                    if (fos != null) {
                                        fos.close();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }).start();
            }
        });
    }

    @org.jetbrains.annotations.Nullable
    private Bitmap getPreview(byte[] data) {

        ExifInterface ei;
        Bitmap rotatedBitmap = null;
        try {
            ei = new ExifInterface(new ByteArrayInputStream(data));
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            Bitmap originBmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateAndCropImage(originBmp, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateAndCropImage(originBmp, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateAndCropImage(originBmp, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = originBmp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rotatedBitmap;
    }

    public Bitmap rotateAndCropImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();

        matrix.postRotate(angle);

        int srcWidth = source.getWidth();
        int srcHeight = source.getHeight();

        Log.d(TAG, "bitmap: " + srcWidth + " : " + srcHeight);

//        float ratio = 85.6f / 54;
//        float height = srcWidth / ratio;
//        float y = (srcHeight - height) / 2;

        float ratio = 85.6f / 54;
        float width = srcHeight / ratio;
        float x = (srcWidth - width) / 2;

        int height = srcHeight;
        float y = (srcHeight - height) / 2;

        return Bitmap.createBitmap(source, (int) x, (int) y, (int) width, height,
                matrix, true);
    }

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private Uri outputMediaFileUri;
    private String outputMediaFileType;

    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), TAG);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "failed to create directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
            outputMediaFileType = "image/*";
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
            outputMediaFileType = "video/*";
        } else {
            return null;
        }
        outputMediaFileUri = Uri.fromFile(mediaFile);
        return mediaFile;
    }

    public Uri getOutputMediaFileUri() {
        return outputMediaFileUri;
    }

    public String getOutputMediaFileType() {
        return outputMediaFileType;
    }


    ///////////////////////////////////////////////////////////////////////////
    // lifecycle
    ///////////////////////////////////////////////////////////////////////////


    @Override
    public void onPause() {
        super.onPause();
        if (mHolder != null) {
            mHolder.removeCallback(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHolder != null) {
            mHolder.addCallback(this);
        }
    }
}
