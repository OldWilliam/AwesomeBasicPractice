package me.jim.wx.javamodule.SwordRefersToOffer;

/**
 * Date: 2019/8/12
 * Name: wx
 * Description:
 */
public class Fibonacci {

    public static void main(String[] args) {
        int fibonacci = new Fibonacci().fibonacci(3);
        System.out.println(fibonacci);
    }

    //优化版本，其实只需要保存两个数
    private int fibonacci(int n) {
        if (n == 0 || n == 1) {
            return n;
        }

        int preA = 1;
        int preB = 1;

        int res = 1;
        for (int i = 2; i < n; i++) {
            res = preA + preB;
            preA = preB;
            preB = res;
        }
        return res;
    }

    public int fibonacci1(int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        int[] num = new int[n];

        num[0] = 1;
        num[1] = 1;

        for (int i = 2; i < n; i++) {
            num[i] = num[i - 1] + num[i - 2];
        }
        return num[n - 1];
    }
}
