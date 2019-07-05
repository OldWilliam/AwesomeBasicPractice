package me.jim.wx.javamodule.leetcode.linkedlist;

/**
 * Date: 2019/6/20
 * Name: wx
 * Description: 相交链表，其实是环形链表的变种
 */
public class IntersectionNodeSolution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode it = headA;
        while (it.next != null) {
            it = it.next;
        }
        ListNode tail = it;
        tail.next = headB;//变成求环形链表的环的入口了

        ListNode intersectionNode = hasCycleII(headA);
        tail.next = null;//要求：不要改变链表结构
        return intersectionNode;
    }

    /**
     * 判断环的入口
     *
     * 先求出环的长度n，然后快慢指针相隔n依次遍历，直到快慢指针相等，那就是入口
     */
    public ListNode hasCycleII(ListNode head) {

        ListNode quick = head;
        ListNode slow = head;

        boolean hasMeet = false;
        int cycleSize = 0;

        while (true) {
            if (slow.next != null) {
                slow = slow.next;
            } else {
                return null;
            }
            if (quick.next != null && quick.next.next != null) {
                quick = quick.next.next;
            } else {
                return null;
            }

            if (quick == slow) {
                if (hasMeet) {
                    break;
                } else {
                    hasMeet = true;
                }
            }

            if (hasMeet) {
                cycleSize++;//整数倍的圈数
            }
        }

        quick = head;
        slow = head;

        while (cycleSize-- > 0) {
            quick = quick.next;
        }

        while (slow != quick) {
            quick = quick.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 别人家的解法 Orz
     *
     * 原始相交链表：
     * a -> b -> c
     *              \
     *                 i -> j-> k -> null
     *      1 -> 2 /
     *
     * 算法遍历的序列
     * pA: a、b、c、i、j、k、1、2、i、j、k、null
     * pB：1、2、i、j、k、a、b、c、i、j、k、null
     */
    public class Solution {
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            /**
             定义两个指针, 第一轮让两个到达末尾的节点指向另一个链表的头部, 最后如果相遇则为交点(在第一轮移动中恰好抹除了长度差)
             两个指针等于移动了相同的距离, 有交点就返回, 无交点就是各走了两条指针的长度
             **/
            if(headA == null || headB == null) return null;
            ListNode pA = headA, pB = headB;
            // 在这里第一轮体现在pA和pB第一次到达尾部会移向另一链表的表头, 而第二轮体现在如果pA或pB相交就返回交点, 不相交最后就是null==null
            while(pA != pB) {
                pA = pA == null ? headB : pA.next;
                pB = pB == null ? headA : pB.next;
            }
            return pA;
        }
    }

}
