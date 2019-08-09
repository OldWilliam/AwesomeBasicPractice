package me.jim.wx.annotationcompiler;

import java.util.HashMap;

/**
 * Date: 2019/8/9
 * Name: wx
 * Description:
 */
public class BindingSets {


    private static HashMap<String, String> bindingMap = new HashMap<>() ;
    static {
        bindingMap.put("FrameLayout", "android.widget.FrameLayout");
        bindingMap.put("TextView", "android.widget.TextView");
    }

    public static String get(String name) {
        String fullName = bindingMap.get(name);
        if (fullName != null) {
            return fullName;
        }
        return name;
    }
}
