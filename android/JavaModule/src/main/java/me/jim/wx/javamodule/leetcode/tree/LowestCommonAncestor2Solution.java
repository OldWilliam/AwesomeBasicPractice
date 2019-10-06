package me.jim.wx.javamodule.leetcode.tree;

import me.jim.wx.javamodule.model.TreeNode;

/**
 * Date: 2019/8/14
 * Name: wx
 * Description: 普通二叉树
 * https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/
 */
public class LowestCommonAncestor2Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        if (root == null) {
            return null;
        }
        if (root == p || root == q) {
            return root;
        }
        //左边有p或者q返回不为空
        TreeNode leftCommon = lowestCommonAncestor(root.left, p, q);
        //右边有p或者q返回不为空
        TreeNode rightCommon = lowestCommonAncestor(root.right, p, q);

        //左右不为空，则为LCA
        if (leftCommon != null && rightCommon != null) {
            return root;
        } else {
            //一边为空，一边不为空，返回节点往上回溯
            if (leftCommon == null && rightCommon != null) {
                return rightCommon;
            }
            if (rightCommon == null && leftCommon != null) {
                return leftCommon;
            }
        }
        return null;
    }
}
