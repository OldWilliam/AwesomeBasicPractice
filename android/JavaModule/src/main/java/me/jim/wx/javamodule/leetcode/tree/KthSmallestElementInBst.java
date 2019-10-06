package me.jim.wx.javamodule.leetcode.tree;

import java.util.ArrayList;
import java.util.List;

import me.jim.wx.javamodule.model.TreeNode;

/**
 * Date: 2019/6/25
 * Name: wx
 * Description:
 * <p>
 * 示例 1:
 * <p>
 * 输入: root = [3,1,4,null,2], k = 1
 * 3
 * / \
 * 1   4
 * \
 * 2
 * 输出: 1
 */
public class KthSmallestElementInBst {
    public int kthSmallest(TreeNode root, int k) {
        List<Integer> list = new ArrayList<>();

        visitNode(root, list);
        return list.get(k - 1);
    }

    private void visitNode(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        visitNode(root.left, list);
        list.add(root.val);
        visitNode(root.right, list);
    }
}
