package me.jim.wx.javamodule.leetcode.array;

/**
 * Date: 2019/6/21
 * Name: wx
 * Description:
 *
 * 输入: [7,1,5,3,6,4]
 * 输出: 7
 * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 *      随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MaxProfitSolution2 {

    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        new MaxProfitSolution2().maxProfit(prices);
    }
    /**
     * 思路：波峰-波谷
     */
    public int maxProfit(int[] prices) {
        if (prices == null) {
            return 0;
        }
        int length = prices.length;
        if (length == 0 || length == 1) {
            return 0;
        }
        if (length == 2) {
            int profit = prices[1] - prices[0];
            return profit > 0 ? profit : 0;
        }

        int totalProfit = 0;
        int volley = 0;
        int top = 0;
        for (int i = 1; i < length; i++) {

            //[7,1,5,3,6,4]
            while (i < length && prices[i] < prices[i - 1]) {
                i++;
            }
            if (i != length) {
                volley = i - 1;
            }else {
                break;
            }


            while (i < length && prices[i] > prices[i - 1]) {
                i++;
            }
            top = i - 1;

            totalProfit += (prices[top] - prices[volley]);
        }
        return totalProfit > 0 ? totalProfit : 0;
    }
}
