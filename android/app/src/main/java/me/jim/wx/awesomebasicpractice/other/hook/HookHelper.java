package me.jim.wx.awesomebasicpractice.other.hook;

import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.jim.wx.awesomebasicpractice.util.ContextHelper;

/**
 * Created by wx on 2018/4/21.
 */

public class HookHelper {
    public static void hookClickListener(View view) {
        try {
            //最新的Android版本不能用反射？

            //拿到listenerinfo
            Method getListenerInfo = View.class.getDeclaredMethod("getListenerInfo");
            getListenerInfo.setAccessible(true);
            Object listenerInfo = getListenerInfo.invoke(view);

            //拿到onclickListener
            Class<?> listenerInfoClz = Class.forName("android.view.View$ListenerInfo");
            Field mOnClickListener = listenerInfoClz.getDeclaredField("mOnClickListener");
            mOnClickListener.setAccessible(true);
            View.OnClickListener originOnClickListener = (View.OnClickListener) mOnClickListener.get(listenerInfo);

            //替换
            View.OnClickListener hookedListener = new AgentListener(originOnClickListener);
            mOnClickListener.set(listenerInfo, hookedListener);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static class AgentListener implements View.OnClickListener{
        private View.OnClickListener innerListener;

        public AgentListener(View.OnClickListener innerListener) {
            this.innerListener = innerListener;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(ContextHelper.getContext(), "HOOK", Toast.LENGTH_SHORT).show();
        }
    }
}
