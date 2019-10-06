package me.jim.wx.javamodule.SwordRefersToOffer;

import me.jim.wx.javamodule.model.TreeNode;

/**
 * Date: 2019/8/13
 * Name: wx
 * Description: 树的子结构
 */
public class HasSubTree {
    public boolean HasSubtree(TreeNode root1,TreeNode root2) {
        if (root1 == null || root2 == null) {
            return false;
        }

        return isSubTree(root1, root2) || HasSubtree(root1.left, root2) || HasSubtree(root1.right, root2);
    }

    private boolean isSubTree(TreeNode root1, TreeNode root2) {
        if (root2 == null) {
            return true;
        }
        if (root1 == null) {
            return false;
        }
        return root1.val == root2.val && isSubTree(root1.left, root2.left) && isSubTree(root1.right, root2.right);
    }
}
