package me.jim.wx.javamodule.leetcode;

import java.util.HashSet;

/**
 * Date: 2019/6/27
 * Name: wx
 * Description:
 */
public class HappyNumber {
    public boolean isHappy(int n) {
        HashSet<Integer> set = new HashSet<>();
        while (true) {
            int result = 0;
            while (n > 0) {
                int a = n % 10;
                result += a * a;
                n = n / 10;
            }
            if (set.contains(result)) {
                return false;
            } else {
                set.add(result);
            }
            if (result == 1) {
                return true;
            } else {
                n = result;
            }
        }
    }
}
