package me.jim.wx.javamodule.leetcode.strings;

/**
 * Date: 2019/6/20
 * Name: wx
 * 示例 1：
 * <p>
 * 输入：["h","e","l","l","o"]
 * 输出：["o","l","l","e","h"]
 */
public class ReverseStringSolution {
    public static void main(String[] args) {
        char[] s = new char[]{'H','a','n','n','a','h'};
        new ReverseStringSolution().reverseString(s);
        System.out.println(s);
    }
    private void reverseString(char[] s) {
        if (s != null && s.length > 1) {
            int i = 0;
            int j = s.length - 1;

            int n = s.length / 2;
            while (n-- > 0) {
                char tmp = s[i];
                s[i] = s[j];
                s[j] = tmp;
                i++;
                j--;
            }
        }
    }
}
