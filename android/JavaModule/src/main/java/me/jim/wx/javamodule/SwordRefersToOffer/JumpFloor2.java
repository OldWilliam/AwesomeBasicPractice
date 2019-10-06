package me.jim.wx.javamodule.SwordRefersToOffer;

/**
 * Date: 2019/8/12
 * Name: wx
 *
 * 一只青蛙一次可以跳上 1 级台阶，也可以跳上 2 级... 它也可以跳上 n 级。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
 */
public class JumpFloor2 {

    public static void main(String[] args) {
        int count = new JumpFloor2().jumpFloor(4);
        System.out.println(count);
    }
    public int jumpFloor(int target) {
        if (target == 0) {
            return 0;
        }
        if (target == 1) {
            return 1;
        }

        int[] ints = new int[target + 1];
        ints[0] = 0;
        ints[1] = 1;
        for (int i = 2; i <= target; i++) {
            int sum = 0;
            for (int j = 0; j < i; j++) {
                sum += ints[j];
            }
            ints[i] = sum + 1;
        }
        return ints[target];
    }
}
