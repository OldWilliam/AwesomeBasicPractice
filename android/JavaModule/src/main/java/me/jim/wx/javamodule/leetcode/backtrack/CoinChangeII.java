package me.jim.wx.javamodule.leetcode.backtrack;

/**
 * Date: 2019/7/2
 * Name: wx
 * Description:
 */
public class CoinChangeII {
    public int change(int amount, int[] coins) {
        for (int i = 0; i < coins.length; i++) {
            innerChange(coins, amount, i);
        }
        return -1;
    }

    private void innerChange(int[] coins, int amount, int i) {
        innerChange(coins, amount - coins[i], i);
    }
}
