package me.jim.wx.javamodule.leetcode.linkedlist;

/**
 * Date: 2019/6/20
 * Name: wx
 * Description: 反转链表
 */
public class ReverseListSolution {

    /**
     * 头插法，迭代
     */
    public ListNode reverseListIterator(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode it = head;

        ListNode preHead = new ListNode(-1);

        while (it != null) {
            ListNode next = it.next;
            it.next = preHead.next;
            preHead.next = it;
            it = next;
        }
        return preHead.next;
    }

    public ListNode reverseListRecursion(ListNode head) {
        //空节点或者一个节点都不行
        if (head == null || head.next == null) return head;
        ListNode p = reverseListRecursion(head.next);
        //1 <- 2 <- 3 <- 4 <- 5(p)
        head.next.next = head;
        head.next = null;
        return p;
    }
}
