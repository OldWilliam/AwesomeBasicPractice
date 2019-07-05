package me.jim.wx.javamodule.leetcode.strings;

/**
 * Date: 2019/6/20
 * Name: wx
 *
 * 输入: "Let's take LeetCode contest"
 * 输出: "s'teL ekat edoCteeL tsetnoc"
 */
public class ReverseWordsSolution {
    public static void main(String[] args) {
        String s = "Let's take LeetCode contest";
        String reverseWords = new ReverseWordsSolution().reverseWords(s);
        System.out.println(reverseWords);
    }

    private String reverseWords(String s) {
        String[] words = s.split(" ");

        StringBuilder stringBuilder = new StringBuilder();
        for (String word : words) {
            char[] chars = word.toCharArray();

            int i = 0;
            int j = chars.length - 1;
            int n = chars.length / 2;
            while (n-- > 0) {
                char tmp = chars[i];
                chars[i] = chars[j];
                chars[j] = tmp;
                i++;
                j--;
            }
            stringBuilder.append(chars).append(" ");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
}
