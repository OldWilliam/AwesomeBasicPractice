package me.jim.wx.javamodule.leetcode;

/**
 * Date: 2019/7/1
 * Name: wx
 * Description:
 */
public class SumOfNumber {
    public static void main(String[] args) {
        System.out.println(new SumOfNumber().getSum(-2, 3));
    }

    public int getSum(int a, int b) {
        System.out.println(a + " " + b);
        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }
        return getSum((a ^ b), (a & b) << 1);
    }
}
