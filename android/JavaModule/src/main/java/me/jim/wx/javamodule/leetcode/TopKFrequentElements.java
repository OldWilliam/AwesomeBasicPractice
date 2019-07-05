package me.jim.wx.javamodule.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Date: 2019/7/2
 * Name: wx
 * Description:
 */
public class TopKFrequentElements {

    public static void main(String[] args) {
        new TopKFrequentElements().topKFrequent(new int[]{5, 3, 1, 1, 1, 3, 5, 73, 1}, 3);
    }

    public List<Integer> topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.merge(num, 1, (a, b) -> a + b);
        }
        Object[] objects = map.keySet().toArray();

        createHeap(objects, map);
        ArrayList<Integer> res = new ArrayList<>(k);
        for (int len = objects.length - 1; len >= objects.length - k; len--) {
            swap(0, len, objects);
            res.add((Integer) objects[len]);
            adjustHeap(objects, map, 0, len);
        }

        return res;
    }

    private void createHeap(Object[] nums, HashMap<Integer, Integer> map) {
        int lastRoot = nums.length / 2 - 1;
        for (int root = lastRoot; root >= 0; root--) {
            adjustHeap(nums, map, root, nums.length);
        }
    }

    private void adjustHeap(Object[] nums, HashMap<Integer, Integer> map, int root, int length) {
        int lastRoot = length / 2 - 1;
        while (root <= lastRoot) {
            int left = root * 2 + 1;
            int right = root * 2 + 2;
            int largest = root;
            if (left <= length - 1) {
                if (map.get(nums[left]) > map.get(nums[largest])) {
                    largest = left;
                }
            }

            if (right <= length - 1) {
                if (map.get(nums[right]) > map.get(nums[largest])) {
                    largest = right;
                }
            }

            if (root != largest) {
                swap(root, largest, nums);
                root = largest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j, Object[] arr) {
        Object tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
