package me.jim.wx.awesomebasicpractice.other.annotation;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by wx on 2018/4/23.
 */

@ClassPreamble(author = "weixin", date = "2018/4/23", description = "注解管理")
public class AnnotationManager {
    private static final AnnotationManager ourInstance = new AnnotationManager();

    public static AnnotationManager ins() {
        return ourInstance;
    }

    private AnnotationManager() {
    }

    /**
     * 获取类注解信息
     */
    public <T extends Object> String getClassAnnotation(Class<T> t) {
        ClassPreamble preamble =  t.getAnnotation(ClassPreamble.class);
        return preamble.toString();
    }

    /**
     * 获取方法注解信息
     * @param clzName
     */

    @RequiresApi(api = Build.VERSION_CODES.N)
    @MethodAnnotation
    @Deprecated
    public String getMethodAnnotation(String clzName) {
        StringBuilder sb = new StringBuilder();
        try {
            Class clazz = Class.forName(clzName);
            for (Method m : clazz.getMethods()) {
                for (Annotation annotation : m.getDeclaredAnnotations()) {
                    sb.append(annotation.toString().concat("/n"));
                }
            }
            return sb.toString();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
