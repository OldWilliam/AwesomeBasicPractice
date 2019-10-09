package me.jim.wx.fragmentannotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Date: 2019-10-09
 * Name: wx
 * Description: Fragment自动添加
 */

@Retention(RetentionPolicy.CLASS)
public @interface AttachFragment {
    String value();
}
