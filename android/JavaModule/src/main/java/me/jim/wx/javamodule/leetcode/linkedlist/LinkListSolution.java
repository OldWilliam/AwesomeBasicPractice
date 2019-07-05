package me.jim.wx.javamodule.leetcode.linkedlist;

/**
 * Date: 2019/6/20
 * Name: wx
 * Description:  链表
 */
public class LinkListSolution {
    public static void main(String[] args) {

    }

    /**
     * 21. 合并两个有序链表
     * 输入：1->2->4, 1->3->4
     * 输出：1->1->2->3->4->4
     *
     * 插入排序思想
     *
     * 其他思路：
     * 1、别用插入排序，链表耦合，增加复杂度。想象一个新链表，l1,l2依次往下链接
     * 2、递归
     */
    class MergeTwoListsSolution {
        public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
            if (l1 == null) {
                return l2;
            }
            if (l2 == null) {
                return l1;
            }
            ListNode preHead = new ListNode(-1);
            preHead.next = l1;

            ListNode pre = preHead;
            ListNode origin = l1;
            ListNode newNode = l2;

            while (origin != null && newNode != null) {
                while (newNode != null && newNode.val <= origin.val) {//新节点可能一直小于
                    ListNode nextNode = newNode.next;

                    pre.next = newNode;
                    newNode.next = origin;


                    newNode = nextNode;
                    pre = pre.next;
                }

                if (newNode != null) {
                    origin = origin.next;
                    pre = pre.next;
                }
            }

            if (newNode != null) {
                pre.next = newNode;
            }
            return preHead.next;
        }
    }

    /**
     * 237. 删除链表中的节点
     * 删除指定节点，但是没有给链表头
     */
    class DeleteNodeSolution {
        public void deleteNode(ListNode node) {
            ListNode next = node.next;
            node.val = next.val;
            node.next = next.next;
        }
    }


}
