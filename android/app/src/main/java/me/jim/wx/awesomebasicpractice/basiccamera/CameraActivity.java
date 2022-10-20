package me.jim.wx.awesomebasicpractice.basiccamera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.util.UtilsKt;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UtilsKt.setFullscreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
