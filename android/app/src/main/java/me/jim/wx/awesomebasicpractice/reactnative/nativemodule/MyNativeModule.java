package me.jim.wx.awesomebasicpractice.reactnative.nativemodule;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import me.jim.wx.awesomebasicpractice.MainActivity;

/**
 * Created by wx on 2017/12/14.
 *
 * React Native创建原生模块
 *
 * http://www.heqiangfly.com/2017/01/14/react-native-native-modules/
 * https://facebook.github.io/react-native/docs/native-modules-android.html
 */

public class MyNativeModule extends ReactContextBaseJavaModule {

    private final static String MODULE_NAME = "MyNativeModule";
    private static final int CODE = 123;
    private ReactApplicationContext mContext;
    private static final String TestEvent = "TestEvent";

    public MyNativeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    //返回模块名
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
        mContext.startActivityForResult(intent, CODE, Bundle.EMPTY);
    }

    @ReactMethod
    public void showToast(String msg, int dur) {
        Toast.makeText(mContext, msg, dur).show();
    }

    //js端调用，使用回调
    @ReactMethod
    public void testCallBack(int para1, int para2, Callback callback) {
        int result = para1 + para2;
        callback.invoke(result);
    }

    //直接向js端发送事件
    public void sendEvent() {
        WritableMap params = Arguments.createMap();
        params.putString("module", "MyNativeModule");
        mContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(TestEvent, params);
    }
}
