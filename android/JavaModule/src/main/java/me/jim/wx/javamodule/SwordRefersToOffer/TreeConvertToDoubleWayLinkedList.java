package me.jim.wx.javamodule.SwordRefersToOffer;

import me.jim.wx.javamodule.model.TreeNode;

/**
 * Date: 2019/8/13
 * Name: wx
 * Description:
 * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向。
 */
public class TreeConvertToDoubleWayLinkedList {
    private TreeNode head;
    private TreeNode pre; //全局变量存储前一个节点

    public TreeNode Convert(TreeNode pRootOfTree) {
        middleVisit(pRootOfTree);//使用中序遍历，这样二叉搜索树才是顺序的
        return head;
    }

    private void middleVisit(TreeNode node) {
        if (node == null) {
            return;
        }
        middleVisit(node.left);
        if (pre == null) {
            head = node;
        } else {
            pre.right = node;
        }
        node.left = pre;
        pre = node;

        middleVisit(node.right);
    }
}
