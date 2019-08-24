package me.jim.wx.javamodule.leetcode.array;

import java.util.HashMap;

/**
 * Date: 2019/6/20
 * Name: wx
 * Description: 求众数
 */
public class MajorityElementSolution {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 2, 2, 2, 5, 4, 2};
        int major = new MajorityElementSolution().majorityElement2(array);
        System.out.println(major);
    }

    public int majorityElement(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
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

    /**
     * 别人家的解法：投票算法，众数算做1，非众数算作-1
     */
    public int majorityElement2(int[] array) {
        int major = array[0];
        int count = 0;
        for (int num : array) {
            if (num == major) {
                count++;
            } else {
                count--;
            }
            if (count <= 0) {
                major = num;
                count = 0;
            }
        }

        //需要再遍历一遍
        count = 0;
        for (int num : array) {
            if (num == major) {
                count++;
            }
        }
        return count * 2 > array.length ? major : 0;
    }

}
