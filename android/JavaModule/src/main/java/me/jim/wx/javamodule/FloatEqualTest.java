package me.jim.wx.javamodule;

import java.util.Locale;

/**
 * Date: 2019/6/19
 * Name: wx
 * Description: 浮点数相等测试
 *
 * 但是下面这些是相等的
 * 0.1f - 0.0f == 0.2f - 0.1f
 * 0.4f - 0.3f == 0.5f - 0.4f
 * 0.6f - 0.5f == 0.8f - 0.7f
 * 0.6f - 0.5f == 1.0f - 0.9f
 * 0.7f - 0.6f == 0.9f - 0.8f
 * 0.8f - 0.7f == 1.0f - 0.9f
 */
public class FloatEqualTest {
    public static void main(String[] args) {
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.8f;

        Float A = 1.0f - 0.9f;
        Float B = 0.9f - 0.8f;


        System.out.println("a: " + a + " " + "b: " + b);
        System.out.println(a == b);
        System.out.println(A.equals(B));
        String format = "%.1f";

        System.out.println(String.format(Locale.US, "a: " + format, a) + " " + String.format(Locale.US, "b: " + format, b));
    }
}
