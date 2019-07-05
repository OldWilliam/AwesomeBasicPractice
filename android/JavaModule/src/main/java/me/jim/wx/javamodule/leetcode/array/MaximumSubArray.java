package me.jim.wx.javamodule.leetcode.array;

/**
 * Date: 2019/6/24
 * Name: wx
 * Description:
 * <p>
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * <p>
 * 示例:
 * <p>
 * 输入: [-2,1,-3,4,-1,2,1,-5,4],
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 * 进阶:
 * <p>
 * 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-subarray
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 */
public class MaximumSubArray {
    public static void main(String[] args) {
        int[] ints = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(new MaximumSubArray().maxSubArrayDP(ints));
    }
    @SuppressWarnings("WeakerAccess")
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int max = Integer.MIN_VALUE;
        int len = nums.length - 1;
        for (int i = 0; i <= len; i++) {
            int sum = 0;
            for (int j = i; j <= len; j++) {
                sum += nums[j];
                max = Math.max(sum, max);
            }
        }
        return max;
    }

    //动态规划
    public int maxSubArrayDP(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int res = nums[0];
        for(int i = 1; i < nums.length; i++){
            nums[i] = Math.max(nums[i], nums[i] + nums[i - 1]);
            res = Math.max(res, nums[i]);
        }

        for (int anInt : nums) {
            System.out.println(anInt);
        }

        return res;
    }
}
