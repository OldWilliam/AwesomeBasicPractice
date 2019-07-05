package me.jim.wx.javamodule.leetcode.dynamicprogram;

/**
 * Date: 2019/7/2
 * Name: wx
 * Description:
 */
public class CoinChange {
    public int coinChange(int[] coins, int amount) {
        int[] res = new int[amount + 1];
        for (int n = 1; n <= amount; n++) {
            res[n] = getMinRes(coins, res, n);
        }
        return res[amount];
    }

    private int getMinRes(int[] coins, int[] res, int n) {
        int min = Integer.MAX_VALUE;
        for (int coin : coins) {
            if (n >= coin) {
                int tmp = res[n - coin];
                if (tmp != -1) {
                    min = Math.min(min, tmp + 1);
                }
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min;
    }
}
