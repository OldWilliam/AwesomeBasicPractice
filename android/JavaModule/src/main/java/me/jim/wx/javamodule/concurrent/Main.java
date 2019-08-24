package me.jim.wx.javamodule.concurrent;

/**
 * Date: 2019/8/16
 * Name: wx
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int res[] = new int[1];
        System.out.println(solution.add(10, res));
        System.out.println(res[0]);
    }

    static class Solution{
        public int add(int i, int[] res) {
            if (i == 1) {
                return 1;
            }
            int sum = i + add(i - 1, res);
            if (sum > 4000) {
                res[0] = 1;
            }
            return sum;
        }
    }
}
