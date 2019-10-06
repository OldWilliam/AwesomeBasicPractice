package me.jim.wx.javamodule.SwordRefersToOffer;

import me.jim.wx.javamodule.model.TreeNode;

/**
 * Date: 2019/8/13
 * Name: wx
 * Description:
 * 操作给定的二叉树，将其变换为源二叉树的镜像。
 */
public class TheMirrorOfTree {
    public void Mirror(TreeNode root) {
        if (root == null) {
            return;
        }

        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;

        Mirror(root.left);
        Mirror(root.right);
    }
}
