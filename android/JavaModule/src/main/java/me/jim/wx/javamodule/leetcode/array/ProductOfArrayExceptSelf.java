package me.jim.wx.javamodule.leetcode.array;

/**
 * Date: 2019/6/25
 * Name: wx
 * Description:
 * <p>
 * 给定长度为 n 的整数数组 nums，其中 n > 1，返回输出数组 output ，其中 output[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积。
 * <p>
 * 示例:
 * <p>
 * 输入: [1,2,3,4]
 * 输出: [24,12,8,6]
 * 说明: 请不要使用除法，且在 O(n) 时间复杂度内完成此题。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/product-of-array-except-self
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class ProductOfArrayExceptSelf {
    /**
     * 4 3 2 1 2
     * <p>
     * 1、4、12、24、24
     */

    //O(n2)
    public int[] productExceptSelf(int[] nums) {
        if (nums == null || nums.length == 0 || nums.length == 1) {
            return nums;
        }
        int[] result = new int[nums.length];
        int sum = nums[0];
        result[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            result[i] = sum;
            int num = nums[i];
            for (int j = 0; j < i; j++) {
                result[j] = result[j] * num;
            }
            sum *= num;
        }
        return result;
    }

    //O(n)
    public int[] productExceptSelf2(int[] nums) {
        if (nums == null || nums.length == 0 || nums.length == 1) {
            return nums;
        }
        int k = 1;
        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            res[i] = k;
            k *= nums[i];
        }
        k = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            res[i] *= k;
            k *= nums[i];
        }
        return res;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 思考：空间复杂度为O(1)的解法
    ///////////////////////////////////////////////////////////////////////////
}
