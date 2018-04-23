package me.jim.wx.awesomebasicpractice.other.annotation;

import android.widget.Toast;

import me.jim.wx.awesomebasicpractice.ContextHelper;

/**
 * Created by wx on 2018/4/23.
 */

@ClassPreamble(author = "weixin", date = "2018/4/23", description = "注解管理")
public class AnnotationManager {
    private static final AnnotationManager ourInstance = new AnnotationManager();

    static AnnotationManager getInstance() {
        return ourInstance;
    }

    private AnnotationManager() {
    }

    public void getRuntimeInfo() {
        ClassPreamble preamble = AnnotationManager.class.getAnnotation(ClassPreamble.class);
        Toast.makeText(ContextHelper.getContext(), preamble.toString(), Toast.LENGTH_SHORT).show();
    }
}
