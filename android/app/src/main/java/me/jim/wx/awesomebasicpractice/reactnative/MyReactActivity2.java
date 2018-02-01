package me.jim.wx.awesomebasicpractice.reactnative;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import javax.annotation.Nullable;

import me.jim.wx.awesomebasicpractice.R;

/**
 * Created by wx on 2018/2/1.
 * <p>
 * 这里继承的ReactActivity是直接从源码拷贝过来的
 */

public class MyReactActivity2 extends ReactActivityCopy {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            View view = getLayoutInflater().inflate(R.layout.title_bar, null);
            android.support.v7.app.ActionBar.LayoutParams params = new android.support.v7.app.ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (48 * getResources().getDisplayMetrics().density));
            params.gravity = Gravity.FILL_HORIZONTAL;
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(view, params);
            Toolbar toolbar = (Toolbar) actionBar.getCustomView().getParent();
            toolbar.setContentInsetsAbsolute(0,0);
            toolbar.setContentInsetsRelative(0,0);
            toolbar.setPadding(0,0,0,0);
    }

//        View view = getLayoutInflater().inflate(R.layout.title_bar, null);
//        setSupportActionBar((Toolbar) view);

    }

    @Nullable
    @Override
    protected String getMainComponentName() {
        return "my-react-native-app";
    }
}
