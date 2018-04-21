package me.jim.wx.awesomebasicpractice;

import android.app.Application;
import android.content.Context;

/**
 * Created by wx on 2018/4/21.
 */

public class ContextHelper {
    private static Application instance = null;

    public static void init(Application application) {
        instance = application;
    }

    public static Context getContext() {
        return instance;
    }
}
