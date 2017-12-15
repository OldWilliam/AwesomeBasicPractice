package me.jim.wx.awesomebasicpractice.reactnative.nativeui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nullable;

import me.jim.wx.awesomebasicpractice.view.primary.HexagonImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wx on 2017/12/14.
 * <p>
 * http://reactnative.cn/docs/0.51/native-component-android.html#content
 * 提供原生视图很简单：
 * 1、创建一个ViewManager的子类。
 * 2、实现createViewInstance方法。
 * 3、导出视图的属性设置器：使用@ReactProp（或@ReactPropGroup）注解。
 * 4、把这个视图管理类注册到应用程序包的createViewManagers里。
 * 5、实现JavaScript模块。
 */

/**
 * SimpleViewManager为ViewManager的子类，它自己好友更多派生类
 */
public class ReactCustomImageManager extends SimpleViewManager<HexagonImageView> {
    private static final String TAG = "ReactCustomImageManager";
    private static final String REACT_CLASS = "RCTHexagonImageView";

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    /**
     * 在这里创建原生视图，而且要把自己设置为初始状态
     */
    @Override
    protected HexagonImageView createViewInstance(ThemedReactContext reactContext) {
        HexagonImageView view = new HexagonImageView(reactContext);
//        Bitmap bmp = BitmapFactory.decodeResource(reactContext.getResources(), R.drawable.login_default_1);
//        view.setImageDrawable(new BitmapDrawable(bmp));
        return view;
    }

    /**
     * Js和Java的对象映射关系
     *
     * Boolean -> Bool
     * Integer -> Number
     * Double -> Number
     * Float -> Number
     * String -> String
     * Callback -> function
     * ReadableMap -> Object
     * ReadableArray -> Array
     */
    /**
     * 导出给Js使用的属性
     * 1、使用@ReactProp或@ReactPropGroup注解
     * 2、方法第一个参数为视图实例
     * 3、方法第二个参数为要设置的属性值
     * 4、方法返回值必须为void
     * 5、访问控制必须为public
     */
    @ReactProp(name = "srcUrl")
    public void setSrc(final HexagonImageView view, @Nullable String src) {
        Log.d(TAG, src);
        OkHttpClient mClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(src)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: ");
                InputStream is = response.body().byteStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(is);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }
}
