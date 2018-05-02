package me.jim.wx.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wx on 2018/5/2.
 *
 * 单纯在终端打印被注解的类的名字
 */

@Retention(RetentionPolicy.CLASS)
public @interface PrinterAnnotation {
}
