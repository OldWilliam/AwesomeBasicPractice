package me.jim.wx.javamodule.leetcode.array;

/**
 * Date: 2019/6/20
 * Name: wx
 * Description: 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 *
 * 输入: [2,2,1]
 * 输出: 1
 */
public class SingleNumberSolution {

    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result = result ^ num;
        }
        return result;
    }
}
