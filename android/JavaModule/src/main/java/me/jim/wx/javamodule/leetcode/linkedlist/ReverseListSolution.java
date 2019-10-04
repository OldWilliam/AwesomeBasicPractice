package me.jim.wx.javamodule.leetcode.linkedlist;

import me.jim.wx.javamodule.model.ListNode;

/**
 * Date: 2019/6/20
 * Name: wx
 * Description: 反转链表
 */
public class ReverseListSolution {

    public static void main(String[] args) {

        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        ListNode listNode = new ReverseListSolution().reverseListRecursion(listNode1);
        ListNode newHead = listNode;
    }

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
        head.next = null;//要有这一句，不然原始头节点next会不为空，仍然指向下一个。原始头节点在反转过来后会变为新链表的尾节点，next是一定为空地
        return p;
    }
}
