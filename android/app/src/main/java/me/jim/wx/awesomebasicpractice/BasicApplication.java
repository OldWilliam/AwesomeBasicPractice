package me.jim.wx.awesomebasicpractice;

import android.app.Application;

import me.jim.wx.awesomebasicpractice.util.ContextHelper;

/**
 * Created by wx on 2018/2/1.
 */

public class BasicApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHelper.init(this);
    }
}
