package me.jim.wx.awesomebasicpractice.other;

import android.content.Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Date: 2019/8/9
 * Name: wx
 * Description:
 */
public class LayoutKnife {
    private static final String PACKAGE_NAME = "me.jim.wx.awesomebasicpractice";
    public static void bind(Object object, Context context) {
        try {
            Class<?> aClass = object.getClass().getClassLoader().loadClass(PACKAGE_NAME.concat(".").concat(object.getClass().getSimpleName())+"_Layout");
            Constructor<?> constructor = aClass.getConstructor(object.getClass(), Context.class);
            constructor.newInstance(object, context);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
