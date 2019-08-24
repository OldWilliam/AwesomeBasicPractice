package me.jim.wx.javamodule.leetcode.tree;

import me.jim.wx.javamodule.model.TreeNode;

/**
 * Date: 2019/6/20
 * Name: wx
 * Description: 二叉搜索树
 */
public class LowestCommonAncestorSolution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode result = root;
        while (root != null) {
            if (root.val == p.val || root.val == q.val) {
                result = root;
                break;
            }
            //一个大于root，一个小于root，说明root是他们的一个根节点，不一定是最低的
            if ((p.val > root.val && q.val < root.val) || (p.val < root.val && q.val > root.val)) {
                result = root;
                break;
            }
            //都大于，说明在右节点
            if (p.val > root.val && q.val > root.val) {
                result = root;
                root = root.right;
            }
            //都小于，说明在左节点
            if (p.val < root.val && q.val < root.val) {
                result = root;
                root = root.left;
            }
        }
        return result;
    }
}
