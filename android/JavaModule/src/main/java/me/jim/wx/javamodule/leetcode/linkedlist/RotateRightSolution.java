package me.jim.wx.javamodule.leetcode.linkedlist;

import me.jim.wx.javamodule.model.ListNode;

/**
 * Date: 2019/6/20
 * Name: wx
 *
 * 示例 1:
 *
 * 输入: 1->2->3->4->5->NULL, k = 2
 * 输出: 4->5->1->2->3->NULL
 * 解释:
 * 向右旋转 1 步: 5->1->2->3->4->NULL
 * 向右旋转 2 步: 4->5->1->2->3->NULL
 * 示例 2:
 *
 * 输入: 0->1->2->NULL, k = 4
 * 输出: 2->0->1->NULL
 * 解释:
 * 向右旋转 1 步: 2->0->1->NULL
 * 向右旋转 2 步: 1->2->0->NULL
 * 向右旋转 3 步: 0->1->2->NULL
 * 向右旋转 4 步: 2->0->1->NULL
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/rotate-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class RotateRightSolution {
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }
        if (k == 0) {
            return head;
        }
        ListNode it = head;
        int length = 1;
        while (it.next != null) {
            it = it.next;
            length++;
        }
        it.next = head;//环

        ListNode preHead = it;

        int times = length - k % length;
        while (times-- > 0) {
            preHead = preHead.next;
        }
        head = preHead.next;
        preHead.next = null;
        return head;
    }
}
