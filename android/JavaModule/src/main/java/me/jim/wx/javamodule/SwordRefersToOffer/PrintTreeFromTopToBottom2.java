package me.jim.wx.javamodule.SwordRefersToOffer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import me.jim.wx.javamodule.model.TreeNode;

/**
 * Date: 2019/8/13
 * Name: wx
 * Description:
 */
public class PrintTreeFromTopToBottom2 {
    ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (pRoot != null) {
            queue.offer(pRoot);
        }
        while (!queue.isEmpty()) {
            int size = queue.size();
            ArrayList<Integer> layerList = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                layerList.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            lists.add(layerList);
        }
        return lists;
    }
}
