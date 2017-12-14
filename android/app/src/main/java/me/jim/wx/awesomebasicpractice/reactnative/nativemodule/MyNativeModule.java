package me.jim.wx.awesomebasicpractice.reactnative.nativemodule;

import android.content.Intent;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import me.jim.wx.awesomebasicpractice.MainActivity;

/**
 * Created by wx on 2017/12/14.
 *
 * React Native创建原生模块
 */

public class MyNativeModule extends ReactContextBaseJavaModule {

    private final static String MODULE_NAME = "MyNativeModule";
    private ReactApplicationContext mContext;
    private static final String TestEvent = "TestEvent";

    public MyNativeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    //导出JavaScript使用的常量
    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> constants = new HashMap<>();
        constants.put("SHORT", Toast.LENGTH_SHORT);
        constants.put("LONG", Toast.LENGTH_LONG);
        constants.put("NATIVE_MODULE_NAME", MODULE_NAME);
        constants.put(TestEvent, TestEvent);
        return constants;
    }

    @ReactMethod
    public void jumpActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    @ReactMethod
    public void showToast(String msg, int dur) {
        Toast.makeText(mContext, msg, dur).show();
    }
}
