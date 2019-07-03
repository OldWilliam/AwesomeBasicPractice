package me.jim.wx.javamodule.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2019/6/24
 * Name: wx
 * Description: 全排列
 * <p>
 * 输入: [1,2,3]
 * 输出:
 * [
 * [1,2,3],
 * [1,3,2],
 * [2,1,3],
 * [2,3,1],
 * [3,1,2],
 * [3,2,1]
 * ]
 * <p>
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/permutations
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Permutations {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        List<List<Integer>> lists = new Permutations().permute2(nums);
        for (List<Integer> list : lists) {
            for (Integer integer : list) {
                System.out.print(integer + "");
            }
            System.out.println();
        }
    }

    /**
     * 1
     * <p>
     * 12、21
     * <p>
     * 312、132、123、321、231、213
     * <p>
     * 思路：插入到每个数字中间
     */
    public List<List<Integer>> permute(int[] nums) {
        if (nums == null) {
            return null;
        }

        List<List<Integer>> lists = new ArrayList<>();
        if (nums.length == 0) {
            lists.add(new ArrayList<Integer>());
            return lists;
        }

        //只有一个数
        ArrayList<Integer> num0 = new ArrayList<>();
        num0.add(nums[0]);
        lists.add(num0);
        if (nums.length == 1) {
            return lists;
        }


        for (int i = 1; i < nums.length; i++) {
            int size = lists.size();
            int num = nums[i];
            List<List<Integer>> newLists = new ArrayList<>();
            for (int n = 0; n < size; n++) {
                List<Integer> ints = lists.get(n);
                for (int j = 0; j <= i; j++) {
                    ArrayList<Integer> newList = new ArrayList<>(ints);
                    newList.add(j, num);//插入不同位置
                    newLists.add(newList);
                }
            }
            lists = newLists;
        }

        return lists;
    }

    /**
     * 回溯法
     * <p>
     * 作者：Code_Granker
     * 来源：CSDN
     * 原文：https://blog.csdn.net/happyaaaaaaaaaaa/article/details/51534048
     * 版权声明：本文为博主原创文章，转载请附上博文链接！
     */
    private List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(res, nums, 0);
        return res;
    }

    private void dfs(List<List<Integer>> res, int[] nums, int head) {
        if (head == nums.length) {
            List<Integer> temp = new ArrayList<>();
            for (int num : nums) temp.add(num);
            res.add(temp);
        }
        for (int i = head; i < nums.length; i++) {
            swap(nums, i, head);
            dfs(res, nums, head + 1);//除head外，剩余子序列的全排列
            swap(nums, i, head);
        }
    }

    private void swap(int[] nums, int m, int n) {
        int temp = nums[m];
        nums[m] = nums[n];
        nums[n] = temp;
    }
}
