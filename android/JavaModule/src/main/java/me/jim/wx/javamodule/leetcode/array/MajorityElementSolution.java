package me.jim.wx.javamodule.leetcode.array;

import java.util.HashMap;

/**
 * Date: 2019/6/20
 * Name: wx
 * Description: 求众数
 */
public class MajorityElementSolution {
    public static void main(String[] args) {

    }

    /**
     * 别人家的解法：投票算法，众数算做1，非众数算作-1
     */
    public int majorityElement(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int num : nums){
            if (map.containsKey(num)) {
                Integer count = map.get(num);
                map.put(num, ++count);

                if (count > nums.length / 2) {
                    return num;
                }
            }
        }
        return -1;
    }
}
