package me.jim.wx.javamodule.SwordRefersToOffer;

import me.jim.wx.javamodule.model.ListNode;

/**
 * Date: 2019/8/12
 * Name: wx
 * Description:
 * 在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。
 * 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5
 */
public class RemoveDuplicateNode {

    public static void main(String[] args) {
        new RemoveDuplicateNode().deleteDuplication(new ListNode(1));
    }

    /**
     *
     * 1、使用两个哨兵指针，一个pre代表确定不重复的，一个last代表重复的里面最后一个，因为是
     * 升序的，所以重复的也是连到一起的
     * 2、构造空的头节点，便于迭代
     */
    public ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null) {
            return null;
        }
        ListNode head = new ListNode(0);
        head.next = pHead;

        ListNode pre = head;
        ListNode last = head.next;

        while (last != null) {
            ListNode start = last;//记录开始的点
            while (last.next != null && last.val == last.next.val) {//如果有重复的，遍历直到最后一个
                last = last.next;
            }
            if (start == last) {
                pre = pre.next;//两个点相等，说明这个点不重复，pre指向它
            } else {
                pre.next = last.next;//删除中间所有重复点，last是最后一个
            }
            last = last.next;
        }
        return head.next;
    }
}
