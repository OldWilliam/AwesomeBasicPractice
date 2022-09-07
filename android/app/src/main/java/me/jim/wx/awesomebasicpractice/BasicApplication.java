package me.jim.wx.awesomebasicpractice;

import android.app.Application;
import android.content.Context;

import me.jim.wx.awesomebasicpractice.other.di.KoinConfigKt;
import me.jim.wx.awesomebasicpractice.util.ContextHelper;



/**
 * Created by wx on 2018/2/1.
 */

public class BasicApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHelper.init(this);
//        BlockCanary.install(this, new BlockCanaryContext()).start();
        KoinConfigKt.initKoin();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        PluginManager.getInstance(base).init();
    }
}
