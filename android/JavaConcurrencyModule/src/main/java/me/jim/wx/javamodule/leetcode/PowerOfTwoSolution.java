package me.jim.wx.javamodule.leetcode;

/**
 * Date: 2019/6/21
 * Name: wx
 * Description:
 */
public class PowerOfTwoSolution {
    public static void main(String[] args) {
        System.out.println(new PowerOfTwoSolution().isPowerOfTwo(16));
    }

    private boolean isPowerOfTwo(int n) {
        if (n < 0) {
            return false;
        }
        int flag = 1;
        for (int i = 0; i < 32; i++) {
            if ((flag << i) == n) {
                return true;
            }
        }
        return false;
    }
}
