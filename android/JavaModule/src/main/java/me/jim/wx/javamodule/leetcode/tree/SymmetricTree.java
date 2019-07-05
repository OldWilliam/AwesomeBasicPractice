package me.jim.wx.javamodule.leetcode.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2019/6/26
 * Name: wx
 * Description:
 */
public class SymmetricTree {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return false;
        }
        List<Integer> integers = new ArrayList<>();

        visitNode(root, integers);

        int l = 0;
        int h = integers.size() - 1;
        while (l < h) {
            if (!integers.get(l).equals(integers.get(h))) {
                return false;
            }
            l++;
            h--;
        }
        return true;
    }

    private void visitNode(TreeNode root, List<Integer> integers) {
        if (root == null) {
            return;
        }
        visitNode(root.left, integers);
        integers.add(root.val);
        visitNode(root.right, integers);
    }
}
