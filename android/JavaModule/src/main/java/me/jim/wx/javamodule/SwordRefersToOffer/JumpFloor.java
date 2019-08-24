package me.jim.wx.javamodule.SwordRefersToOffer;

/**
 * Date: 2019/8/12
 * Name: wx
 * Description:
 *
 *  一只青蛙一次可以跳上 1 级台阶，也可以跳上 2 级。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
 */
public class JumpFloor {
    public int jumpFloor(int target) {
        if (target == 0) {
            return 0;
        }
        if (target == 1) {
            return 1;
        }
        int preA = 0;
        int preB = 1;
        for (int i = 2; i <= target; i++) {
            int res = preA + preB;
            preA = preB;
            preB = res;
        }
        return preA + preB;
    }
}
