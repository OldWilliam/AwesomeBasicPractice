package me.jim.wx.javamodule.leetcode;

/**
 * Date: 2019/6/21
 * Name: wx
 * Description: 回文数
 * <p>
 * 输入: 121
 * 输出: true
 * <p>
 * 输入: -121
 * 输出: false
 */
public class PalindromeSolution {
    public static void main(String[] args) {
        boolean palindrome2 = new PalindromeSolution().isPalindrome2(121);
        System.out.println(palindrome2);
    }
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        int length = String.valueOf(x).length();
        if (length == 1) {
            return true;
        }

        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            int n = x % 10;
            array[i] = n;
            x = x / 10;
        }

        int left = 0;
        int right = length - 1;
        while (left < right) {
            if (array[left] != array[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    private boolean isPalindrome2(int x) {
        if (x < 0) {
            return false;
        }
        int length = String.valueOf(x).length();
        if (length == 1) {
            return true;
        }

        int reverseX = 0;
        int count = length / 2;
        boolean flag = (length % 2 == 1);

        while (count > 0) {
            int n = x % 10;
            x = x / 10;
            double factor = Math.pow(10, count - 1);
            reverseX += factor * n;
            count--;
        }
        if (flag) {
            x = x / 10;
        }
        return reverseX == x;
    }
}
