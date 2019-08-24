package me.jim.wx.javamodule.SwordRefersToOffer;

/**
 * Date: 2019/8/12
 * Name: wx
 * Description:
 */
public class IntegerBitCount {

    public static void main(String[] args) {

        int value = Integer.MIN_VALUE;
        int count = new IntegerBitCount().NumberOf1(value);
        System.out.println(count);
        System.out.println(Integer.bitCount(value));
    }

    public int NumberOf1(int n) {
        int flag = 1;
        int count = 0;
        while (n != 0) {
            if ((n & flag) == 1) {
                count++;
            }
            n = n >>> 1;
        }
        return count;
    }
}
