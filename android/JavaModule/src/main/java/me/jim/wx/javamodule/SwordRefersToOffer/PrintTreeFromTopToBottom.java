package me.jim.wx.javamodule.SwordRefersToOffer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import me.jim.wx.javamodule.model.TreeNode;

/**
 * Date: 2019/8/13
 * Name: wx
 * Description: 从上往下打印出二叉树的每个节点，同层节点从左至右打印。
 */
public class PrintTreeFromTopToBottom {
    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        ArrayList<Integer> list = new ArrayList<>();

        if (root != null) {
            queue.offer(root);
        }
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            list.add(node.val);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return list;
    }
}
