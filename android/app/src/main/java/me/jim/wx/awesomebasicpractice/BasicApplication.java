package me.jim.wx.awesomebasicpractice;

import android.app.Application;
import android.util.Log;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import me.jim.wx.awesomebasicpractice.reactnative.nativemodule.MyReactPackage;

/**
 * Created by wx on 2018/2/1.
 */

public class BasicApplication extends Application implements ReactApplication{

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.asList(
                    new MainReactPackage(),
                    //添加新的模块
                    new MyReactPackage()
            );
        }

        @Nullable
        @Override
        protected String getBundleAssetName() {
            return "index.bundle";
        }

        @Override
        protected String getJSMainModuleName() {
            return "index";
        }

        @Nullable
        @Override
        protected String getJSBundleFile() {
            return super.getJSBundleFile();
        }
    };
}
