package me.jim.wx.javamodule.leetcode.linkedlist;

import me.jim.wx.javamodule.model.ListNode;

/**
 * Date: 2019/6/20
 * Name: wx
 * Description: 判断链表有没有环
 *
 *
 * <dl>
 * <dt>示例 1：</dt>
 * <dd>输入：head = [3,2,0,-4], pos = 1</dd>
 * <dd>输出：true</dd>
 * <dd>解释：链表中有一个环，其尾部连接到第二个节点。</dd>
 * </dl>
 * <p>
 * <img width="320" height="100" src="https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/12/07/circularlinkedlist.png" alt="">
 * </P>
 * <p>
 * 示例 2：
 * 输入：head = [1,2], pos = 0
 * 输出：true
 * 解释：链表中有一个环，其尾部连接到第一个节点。
 * <p>
 * 示例 3：
 * 输入：head = [1], pos = -1
 * 输出：false
 * 解释：链表中没有环。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/linked-list-cycle
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class HasCycleSolution {
    /**
     * 判断是否有环
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        if (head.next == head) {
            return true;
        }

        ListNode quick = head;
        ListNode slow = head;

        while (true) {
            if (slow.next != null) {
                slow = slow.next;
            } else {
                return false;
            }

            if (quick.next != null && quick.next.next != null) {
                quick = quick.next.next;
            } else {
                return false;
            }

            if (slow == quick) {
                return true;
            }
        }
    }

    /**
     * 判断环的入口
     *
     * 先求出环的长度n，然后快慢指针相隔n依次遍历，直到快慢指针相等，那就是入口
     */
    public ListNode hasCycleII(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        if (head.next == head) {
            return head;
        }

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
                cycleSize++;
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
}
