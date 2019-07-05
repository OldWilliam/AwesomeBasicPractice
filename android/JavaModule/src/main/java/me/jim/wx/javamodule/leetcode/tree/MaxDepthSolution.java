package me.jim.wx.javamodule.leetcode.tree;


/**
 * Date: 2019/6/20
 * Name: wx
 * Description:
 */
public class MaxDepthSolution {
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left, right) + 1;//总的高度等于孩子的最高距离+1
    }
}


