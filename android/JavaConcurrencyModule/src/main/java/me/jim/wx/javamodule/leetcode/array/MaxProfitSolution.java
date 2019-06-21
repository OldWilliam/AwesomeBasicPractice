package me.jim.wx.javamodule.leetcode.array;

/**
 * Date: 2019/6/21
 * Name: wx
 * Description:
 * <p>
 * 输入: [7,1,5,3,6,4]
 * 输出: 5
 * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
 * 注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。
 * <p>
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MaxProfitSolution {

    public static void main(String[] args) {
        int[] ints = {7, 1, 5, 3, 6, 4};
        int maxProfit = new MaxProfitSolution().maxProfit(ints);
        System.out.print(maxProfit);
    }

    /**
     * 暴力解法
     */
    private int maxProfit(int[] prices) {
        if (prices == null || prices.length == 1) {
            return 0;
        }
        if (prices.length == 2) {
            return prices[1] - prices[0];
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < prices.length - 1; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                int profit = prices[j] - prices[i];
                if (profit > max) {
                    max = profit;
                }
            }
        }
        return max;
    }

    /**
     * 动态规划
     */
    private int maxProfit2(int[] prices){
        if (prices == null || prices.length == 1) {
            return 0;
        }
        if (prices.length == 2) {
            int max = prices[1] - prices[0];
            return max > 0 ? max : 0;
        }

        int min = prices[0];
        int maxProfit = Integer.MIN_VALUE;
        for (int price : prices) {
            min = Math.min(price, min);
            maxProfit = Math.max(maxProfit, price - min);
        }
        return maxProfit > 0 ? maxProfit : 0;
    }
}
