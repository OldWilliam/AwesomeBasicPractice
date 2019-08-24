package me.jim.wx.awesomebasicpractice;

import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.os.MessageQueue;

import com.didi.virtualapk.PluginManager;

import me.jim.wx.awesomebasicpractice.util.ContextHelper;

import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;


/**
 * Created by wx on 2018/2/1.
 */

public class BasicApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHelper.init(this);
//        BlockCanary.install(this, new BlockCanaryContext()).start();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        PluginManager.getInstance(base).init();
    }
}
