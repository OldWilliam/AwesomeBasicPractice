package me.jim.wx.awesomebasicpractice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import me.jim.wx.awesomebasicpractice.reactnative.MyReactActivity;
import me.jim.wx.awesomebasicpractice.view.primary.FlowLayout;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
