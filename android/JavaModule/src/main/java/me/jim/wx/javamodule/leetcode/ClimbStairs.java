package me.jim.wx.javamodule.leetcode;

/**
 * Date: 2019/6/21
 * Name: wx
 * Description:
 *
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 *
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 *
 * 注意：给定 n 是一个正整数。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/climbing-stairs
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 4 阶
 *
 * 1、1、1、1
 * 1、2、1
 * 2、1、1
 * 1、1、2
 * 2、2
 *
 * 3 阶
 * 1、1、1
 * 1、2
 * 2、1
 *
 * 2阶
 * 1、1
 * 2
 *
 * 1阶
 * 1
 *
 * 0阶
 * 0
 */
public class ClimbStairs {

    public static void main(String[] args) {
        int i = new ClimbStairs().climbStairs(44);
        System.out.println(i);
    }

    /**
     * 递归
     */
    private int climbStairs(int n) {
        if (n == 0 || n == 1 || n == 2) {
            return n;
        }
        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    /**
     * 动态规划
     */
    private int climbStairs2(int n) {
        if (n == 0 || n == 1 || n == 2) {
            return n;
        }
        int[] ints = new int[n];
        ints[0] = 0;
        ints[1] = 1;
        ints[2] = 2;
        for (int i = 3; i < n; i++) {
            ints[i] = ints[i - 1] + ints[i - 2];
        }
        return ints[n - 1] + ints[n - 2];
    }

    ///////////////////////////////////////////////////////////////////////////
    //这题解法真的很多，也很奥妙
    // https://leetcode-cn.com/problems/climbing-stairs/solution/pa-lou-ti-by-leetcode/
    ///////////////////////////////////////////////////////////////////////////
}
