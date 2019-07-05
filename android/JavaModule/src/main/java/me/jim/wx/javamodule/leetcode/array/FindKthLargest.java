package me.jim.wx.javamodule.leetcode.array;

/**
 * Date: 2019/6/26
 * Name: wx
 * Description:
 * <p>
 * 示例 1:
 * <p>
 * 输入: [3,2,1,5,6,4] 和 k = 2
 * 输出: 5
 * 示例 2:
 * <p>
 * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
 * 输出: 4
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/kth-largest-element-in-an-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FindKthLargest {
    public static void main(String[] args) {
        int[] ints = {3, 2, 1, 5, 6, 4};
        int kthLargest = new FindKthLargest().findKthLargest(ints, 2);
        System.out.println(kthLargest);

    }

    public int findKthLargest(int[] nums, int k) {

        int left = 0;
        int right = nums.length - 1;

        while (true) {
            int pivot = qkPass(nums, left, right);
            if (pivot > k - 1) {
                right = pivot - 1;
            }
            if (pivot < k - 1) {
                left = pivot + 1;
            }
            if (pivot == k - 1) {
                break;
            }
        }

        return nums[k - 1];
    }

    private int qkPass(int[] nums, int left, int right) {
        int pivot = nums[left];
        while (left < right) {
            while (nums[right] <= pivot && left < right) {
                right--;
            }
            if (left < right) {
                nums[left] = nums[right];
                left++;
            } else {
                break;
            }

            while (nums[left] >= pivot && left < right) {
                left++;
            }
            if (left < right) {
                nums[right] = nums[left];
                right--;
            }
        }
        nums[left] = pivot;
        return left;
    }
}
