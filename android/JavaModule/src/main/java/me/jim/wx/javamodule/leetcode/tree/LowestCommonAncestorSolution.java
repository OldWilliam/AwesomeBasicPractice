package me.jim.wx.javamodule.leetcode.tree;

/**
 * Date: 2019/6/20
 * Name: wx
 * Description:
 */
public class LowestCommonAncestorSolution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode result = root;
        while (root != null) {
            if (root.val == p.val || root.val == q.val) {
                result = root;
                break;
            }
            if ((p.val > root.val && q.val < root.val) || (p.val < root.val && q.val > root.val)) {
                result = root;
                break;
            }
            if (p.val > root.val && q.val > root.val) {
                result = root;
                root = root.right;
            }
            if (p.val < root.val && q.val < root.val) {
                result = root;
                root = root.left;
            }
        }
        return result;
    }
}
