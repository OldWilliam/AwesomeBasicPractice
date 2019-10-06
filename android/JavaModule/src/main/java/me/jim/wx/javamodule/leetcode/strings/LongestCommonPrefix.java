package me.jim.wx.javamodule.leetcode.strings;

/**
 * Date: 2019/6/24
 * Name: wx
 * Description:
 */
public class LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return null;
        }
        if (strs.length == 1) {
            return strs[0];
        }

        StringBuilder result = new StringBuilder();
        String strMask = strs[0];
        for (int i = 0; i < strMask.length(); i++) {
            char mask = strs[0].charAt(i);
            boolean flag = true;
            for (int j = 1; j < strs.length; j++) {
                String str = strs[j];
                if (i >= str.length() || str.charAt(i) != mask) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result.append(mask);
            } else {
                break;
            }
        }
        return result.toString();
    }
}
