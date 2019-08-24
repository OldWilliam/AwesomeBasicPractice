package me.jim.wx.javamodule.SwordRefersToOffer;

import java.util.ArrayList;

import me.jim.wx.javamodule.model.ListNode;

/**
 * Date: 2019/8/11
 * Name: wx
 * Description:
 */
public class ReverseLinkedList {
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> ret = new ArrayList<>();

        addNode(ret, listNode);

        return ret;
    }

    private void addNode(ArrayList<Integer> ret, ListNode node) {
        if (node == null) {
            return;
        }
        addNode(ret, node.next);
        ret.add(node.val);
    }
}
