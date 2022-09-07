package me.jim.wx.awesomebasicpractice.basiccamera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.view.Surface;

import java.io.File;
import java.io.IOException;

import static me.jim.wx.awesomebasicpractice.basiccamera.CameraFragment.MEDIA_TYPE_VIDEO;

/**
 * Date: 2020-01-07
 */
public class VideoUtil {
    private static final String TAG = "VideoUtil";
    private CameraFragment mCameraFragment;

    private Camera mCamera;
    private MediaRecorder mediaRecorder;

    public VideoUtil(CameraFragment mCameraFragment) {
        this.mCameraFragment = mCameraFragment;
    }

    private boolean isRecording;

    public boolean isRecording() {
        return isRecording;
    }

    public void takeVideo() {

        if (ActivityCompat.checkSelfPermission(mCameraFragment.getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mCameraFragment.getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 0);
            return;
        }

        if (isRecording) {
            // stop recording and release camera
            mediaRecorder.stop();  // stop the recording
            releaseMediaRecorder(); // release the MediaRecorder object
            mCamera.lock();         // take camera access back from MediaRecorder
            isRecording = false;
        } else {
            // initialize video camera
            if (prepareVideoRecorder()) {
                // Camera is available and unlocked, MediaRecorder is prepared,
                // now you can start recording
                mediaRecorder.start();
                isRecording = true;
            } else {
                // prepare didn't work, release the camera
                releaseMediaRecorder();
                // inform user
            }
        }
    }

    private boolean prepareVideoRecorder() {

        mCamera = getCameraInstance();
        mediaRecorder = new MediaRecorder();

        // Step 1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mediaRecorder.setCamera(mCamera);

        // Step 2: Set sources
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 音麦
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);// 视频源


        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P));
        mediaRecorder.setMaxDuration(30 * 1000);
        mediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mr, int what, int extra) {
                Log.d(TAG, what + " " + extra);
            }
        });

        if (mCameraFragment.isFrontCamera()) {// 前置摄像头
            mediaRecorder.setOrientationHint(270);
        } else {
            mediaRecorder.setOrientationHint(90); // 设置视频竖着录出来也能竖着播放
        }

        // Step 4: Set output file
        mediaRecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).toString());

        // Step 5: Set the preview output
        mediaRecorder.setPreviewDisplay(getPreviewSurfaceSurface());

        // Step 6: Prepare configured MediaRecorder
        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();   // clear recorder configuration
            mediaRecorder.release(); // release the recorder object
            mediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }
    }

    private Surface getPreviewSurfaceSurface() {
        return mCameraFragment.getSurface();
    }

    private File getOutputMediaFile(int mediaType) {
        return mCameraFragment.getOutputMediaFile(mediaType);
    }

    private Camera getCameraInstance() {
        return mCameraFragment.getCamera();
    }
}
