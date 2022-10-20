package me.jim.wx.awesomebasicpractice.camera;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.TextureView;
import android.view.View;

import java.math.BigDecimal;

import me.jim.wx.awesomebasicpractice.camera.gl.CameraPreviewRenderer;
import me.jim.wx.awesomebasicpractice.camera.gl.CameraRenderer;

public class CameraPreview implements CameraRenderer.OnRendererReadyListener,
        CameraEngine.CameraPreviewStateListener {

    public static final String TAG = "CameraPreview";

    private Context mContext;
    /**
     * Camera preview textureview
     */
    private TextureView mTextureView;

    private View mPreviewContainView;

    private CameraRenderer mRenderer;
    private CameraEngine mCameraEngine;

    private boolean mRestartCamera = false;

    private volatile CAMERA_STATE mCameraState = CAMERA_STATE.CLOSED;

    public enum CAMERA_STATE {
        PENGING_OPEN,
        PENGING_CLOSE,
        OPENED,
        CLOSED,
    }


    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public CameraPreview(Context context, TextureView textureView, View containView){
        this.mContext = context;
        this.mTextureView = textureView;
        this.mPreviewContainView = containView;

        mCameraEngine = CameraEngine.getInstance(context);
        mCameraEngine.setCameraStateListener(this);
        mCameraEngine.setTextureView(mTextureView);
    }

    public synchronized boolean startCamera(){
        if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            return false;
        }
        mTextureView.setVisibility(View.VISIBLE);

        if(!mTextureView.isAvailable())
            mTextureView.setSurfaceTextureListener(mTextureListener); //set listener to handle when its ready
        else{
            setReady(mTextureView,mTextureView.getSurfaceTexture(),
                    mTextureView.getWidth(), mTextureView.getHeight());
        }

        return true;
    }

    /**
     * kills the camera in camera fragment and shutsdown render thread
     * @param restart whether or not to restart the camera after shutdown is complete
     */
    public void shutdownCamera(boolean restart)
    {
        try{
            //make sure we're here in a working state with proper permissions when we kill the camera
//        if(PermissionsHelper.isMorHigher() && !mPermissionsSatisfied) return;

            //check to make sure we've even created the cam and renderer yet
            if(mCameraEngine == null || mRenderer == null) return;

            mCameraEngine.closeCamera();

            mRestartCamera = restart;
            mRenderer.getRenderHandler().sendShutdown();
            mRenderer = null;

        }catch (Exception e){

        }

    }


    /**
     * called whenever surface texture becomes initially available or whenever a camera restarts after
     * completed recording or resuming from onpause
     * @param surface {@link SurfaceTexture} that we'll be drawing into
     * @param width width of the surface texture
     * @param height height of the surface texture
     */
    protected void setReady(TextureView textureView, SurfaceTexture surface, int width, int height) {
        mRenderer = getRenderer(textureView,surface, width, height);
        mRenderer.setOnRendererReadyListener(this);
        mRenderer.start();
    }

    /**
     * Override this method for easy usage of stock example setup, allowing for easy
     * recording with any shader.
     */
    protected CameraRenderer getRenderer(TextureView textureView, SurfaceTexture surface, int width, int height) {
        return new CameraPreviewRenderer(mContext, textureView,surface, width, height);
    }

    /**
     * {@link android.view.TextureView.SurfaceTextureListener} responsible for setting up the rest of the
     * rendering and recording elements once our TextureView is good to go.
     */
    private TextureView.SurfaceTextureListener mTextureListener = new TextureView.SurfaceTextureListener()
    {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, final int width, final int height) {
            //convenience method since we're calling it from two places
            Log.d(TAG,"onSurfaceTextureAvailable");
            setReady(mTextureView,surface, width, height);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
            mCameraEngine.configureTransform(width, height);
            Log.d(TAG,"onSurfaceTextureSizeChanged");
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            Log.d(TAG,"onSurfaceTextureDestroyed");
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };

    //openGL 初始化成功
    @Override
    public void onRendererReady() {
        Log.d(TAG,"onRendererReady");
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try{
                    mCameraEngine.setPreviewTexture(mRenderer.getPreviewTexture());
                    mCameraEngine.openCamera(mTextureView.getWidth(),mTextureView.getHeight());
                    float previewW = mCameraEngine.getPreviewSize().getWidth();
                    float previewH = mCameraEngine.getPreviewSize().getHeight();

                    BigDecimal bigDecimalWidth = new BigDecimal(Double.toString(previewW));
                    BigDecimal bigDecimalHeight = new BigDecimal(Double.toString(previewH));
                    BigDecimal previewWHRatio = bigDecimalHeight.divide(bigDecimalWidth);

                    mRenderer.setPreivewWHRatio(previewWHRatio.floatValue());
                    
                    mCameraState = CAMERA_STATE.PENGING_OPEN;
                }catch (Exception e){
                    Log.e(TAG,e.toString(),e);
                }

            }
        });
    }

    @Override
    public void onRendererFinished() {
        Log.d(TAG,"onRendererFinished");
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mRestartCamera) {
                    setReady(mTextureView,mTextureView.getSurfaceTexture(),mTextureView.getWidth(), mTextureView.getHeight());
                    mRestartCamera = false;
                }
            }
        });
    }

    @Override
    public void onCameraStartPreview(boolean isSuccess) {
        Log.d(TAG,"onCameraStartPreview");
        mCameraState = CAMERA_STATE.OPENED;
        mPreviewContainView.setAlpha(1f);
        doRotation();
    }

    @Override
    public void onCameraStopPreview(boolean isSuccess) {
        Log.d(TAG,"onCameraStopPreview");
        mCameraState = CAMERA_STATE.CLOSED;
    }

    public void setPendingRotationDiff(int pendingRotationDiff,boolean doNow) {
        mPendingRotationDiff = pendingRotationDiff;
        if(doNow){
            doRotation();
        }
    }

    private int mPendingRotationDiff = 0;


    private void doRotation() {
        if(mPendingRotationDiff == 0){
            return;
        }
        if(mTextureView.isAvailable()){
            Matrix matrix = new Matrix();
            matrix.setRotate(mPendingRotationDiff,mTextureView.getPivotX(),mTextureView.getPivotY());
            mTextureView.setTransform(matrix);
        }/*else{
            if(BuildConfig.DEBUG){
                throw new IllegalStateException("mTextureView is not isAvailable()");
            }
        }*/
        mPendingRotationDiff = 0;
    }


}
