package me.jim.wx.javamodule.leetcode.array;

/**
 * Date: 2019/6/21
 * Name: wx
 * Description:
 *
 * 给定两个有序整数数组 nums1 和 nums2，将 nums2 合并到 nums1 中，使得 num1 成为一个有序数组。
 *
 * 说明:
 *
 * 初始化 nums1 和 nums2 的元素数量分别为 m 和 n。
 * 你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。
 * 示例:
 *
 * 输入:
 * nums1 = [1,2,3,0,0,0], m = 3
 * nums2 = [2,5,6,9],     n = 4
 *
 * 输出: [1,2,2,3,5,6]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-sorted-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MergeSortedArray {
    public static void main(String[] args) {
        int[] num1 = {4,0,0,0,0,0};
        int[] num2 = {1,2,3,5,6};
        new MergeSortedArray().merge(num1, 1, num2, 5);
        for (int i : num1) {
            System.out.println(i);
        }
    }
    private void merge(int[] nums1, int m, int[] nums2, int n) {
        if (nums2 == null || nums2.length == 0) {
            return;
        }
        int i1 = nums1.length - n - 1;
        int i2 = nums2.length - 1;

        if (m == 0) {
            System.arraycopy(nums2, 0, nums1, 0, n);
            return;
        }

        for (int i = nums1.length - 1; i >= 0; i--) {
            int a = nums1[i1];
            int b = nums2[i2];
            if (a >= b) {
                nums1[i] = a;
                i1--;
            }else {
                nums1[i] = b;
                i2--;
            }
            if (i1 < 0 || i2 < 0) {
                break;
            }
        }

        if (i1 < 0 && i2 >= 0) {
            System.arraycopy(nums2, 0, nums1, 0, i2 + 1);
        }
    }
}
