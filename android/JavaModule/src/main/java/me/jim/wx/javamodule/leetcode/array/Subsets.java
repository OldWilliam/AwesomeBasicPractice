package me.jim.wx.javamodule.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2019/6/24
 * Name: wx
 * Description:
 * 1
 * 1、2  2
 * （1、3）  （1、2、3） （2、3） 3
 */
public class Subsets {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        List<List<Integer>> subsets = new Subsets().subsets3(nums);
        for (List<Integer> list : subsets) {
            for (Integer integer : list) {
                System.out.print(integer + "");
            }
            System.out.println();
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        ArrayList<List<Integer>> lists = new ArrayList<>();
        ArrayList<Integer> num0 = new ArrayList<>();
        num0.add(nums[0]);
        lists.add(num0);
        if (nums.length == 1) {
            lists.add(new ArrayList<Integer>());
            return lists;
        }
        for (int j = 1; j < nums.length; j++) {
            int num = nums[j];
            int size = lists.size();//边迭代边加元素，不能重复计算size，否则死循环
            for (int i = 0; i < size; i++) {
                //还要保留以前的数据
                ArrayList<Integer> integers = new ArrayList<>(lists.get(i));
                integers.add(num);
                lists.add(integers);
            }
            ArrayList<Integer> list = new ArrayList<>();
            list.add(num);
            lists.add(list);
        }
        lists.add(new ArrayList<Integer>());
        return lists;
    }


    ///////////////////////////////////////////////////////////////////////////
    // 递归
    ///////////////////////////////////////////////////////////////////////////
    private List<List<Integer>> subsets2(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();
        find(result, nums, 0);
        return result;
    }

    private void find(List<List<Integer>> result, int[] nums, int head) {

        if (head == nums.length - 1) {
            ArrayList<Integer> tmp = new ArrayList<>();
            tmp.add(nums[head]);
            result.add(tmp);
            return;
        }

        find(result, nums, head + 1);

        int size = result.size();
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> tmp = new ArrayList<>(result.get(i));
            tmp.add(nums[head]);
            result.add(tmp);
        }
        ArrayList<Integer> single = new ArrayList<>();
        single.add(nums[head]);
        result.add(single);
    }


    ///////////////////////////////////////////////////////////////////////////
    // 回溯法
    //
    // 作者：powcai
    // 链接：https://leetcode-cn.com/problems/two-sum/solution/hui-su-suan-fa-by-powcai-5/
    // 来源：力扣（LeetCode）
    // 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 输出结果
     * 1
     * 12
     * 123
     * 13
     * 2
     * 23
     * 3
     */
    public List<List<Integer>> subsets3(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(0, nums, res, new ArrayList<Integer>());
        return res;
    }

    private void backtrack(int head, int[] nums, List<List<Integer>> res, ArrayList<Integer> tmp) {
        res.add(new ArrayList<>(tmp));
        for (int i = head; i < nums.length; i++) {
            tmp.add(nums[i]);
            backtrack(i + 1, nums, res, tmp);
            tmp.remove(tmp.size() - 1);
        }
    }
}
