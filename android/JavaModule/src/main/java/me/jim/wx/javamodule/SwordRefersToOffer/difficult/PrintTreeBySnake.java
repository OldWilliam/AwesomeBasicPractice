package me.jim.wx.javamodule.SwordRefersToOffer.difficult;

import java.util.ArrayList;
import java.util.Stack;

import me.jim.wx.javamodule.model.TreeNode;

/**
 * Date: 2019/8/11
 * Name: wx
 * Description:之字形（蛇形）打印链表
 * <p>
 * {8,6,10,5,7,9,11}
 */
public class PrintTreeBySnake {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(8);
        root.left = new TreeNode(6);
        root.right = new TreeNode(10);
        root.left.left = new TreeNode(5);
        root.left.right = new TreeNode(7);
        root.right.left = new TreeNode(9);
        root.right.right = new TreeNode(11);

        new PrintTreeBySnake().Print(root);
    }

    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        if (pRoot != null) {
            stack1.push(pRoot);
        }
        int depth = 1;

        ArrayList<ArrayList<Integer>> ret = new ArrayList<>();

        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            Stack<TreeNode> frontStack;
            Stack<TreeNode> backStack;
            if (depth % 2 == 1) { //重要
                frontStack = stack1;
                backStack = stack2;
            } else {
                frontStack = stack2;
                backStack = stack1;
            }

            int size = frontStack.size();
            ArrayList<Integer> layer = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = frontStack.pop();
                layer.add(node.val);

                if (depth % 2 == 1) { //重要
                    if (node.left != null) {
                        backStack.push(node.left);
                    }
                    if (node.right != null) {
                        backStack.push(node.right);
                    }
                } else {
                    if (node.right != null) {
                        backStack.push(node.right);
                    }
                    if (node.left != null) {
                        backStack.push(node.left);
                    }
                }

            }
            ret.add(layer);
            depth++;
        }
        return ret;
    }
}
