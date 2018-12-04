package me.jim.wx.awesomebasicpractice.other.annotation;

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

    public <T extends Object> String getPreamble(Class<T> t) {
        ClassPreamble preamble =  t.getAnnotation(ClassPreamble.class);
        return preamble.toString();
    }
}
