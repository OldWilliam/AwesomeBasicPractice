package me.jim.wx.awesomebasicpractice.reactnative;


import javax.annotation.Nullable;

/**
 * Created by wx on 2018/2/1.
 *
 * 这里继承的ReactActivity是直接从源码拷贝过来的
 */

public class MyReactActivity2 extends ReactActivityCopy {
    @Nullable
    @Override
    protected String getMainComponentName() {
        return "my-react-native-app";
    }
}
