package me.jim.wx.javamodule.leetcode.tree;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Date: 2019/7/5
 * Name: wx
 * Description: 二叉树序列化和反序列化化
 */
public class TreeCodecSolution {
    interface ITreeCodec {
        // Encodes a tree to a single string.
        String serialize(TreeNode root);

        // Decodes your encoded data to tree.
        TreeNode deserialize(String data);
    }


    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        //        root.left = new TreeNode(2);
        //        root.right = new TreeNode(3);
        //        root.right.left = new TreeNode(4);
        //        root.right.right = new TreeNode(5);

        ITreeCodec treeCodec = new DFSTreeCodec();
        String serialize = treeCodec.serialize(root);
        System.out.println(serialize);
        treeCodec.deserialize(serialize);
    }

    /**
     * Description: 层序遍历的解法
     */
    public static class BFSTreeCodec implements ITreeCodec {


        // Encodes a tree to a single string.
        @Override
        public String serialize(TreeNode root) {
            if (root == null) {
                return null;
            }
            int depth = depth(root);
            int fullNode = (int) (Math.pow(2, depth) - 1);
            StringBuilder builder = new StringBuilder();
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty() && fullNode > 0) {
                TreeNode node = queue.poll();
                fullNode--;
                if (node != null) {
                    queue.offer(node.left);
                    queue.offer(node.right);
                    builder.append(node.val).append(",");
                } else {
                    queue.offer(null);
                    queue.offer(null);
                    builder.append("null,");
                }
            }
            return builder.toString();
        }

        private int depth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            if (root.left == null && root.right == null) {
                return 1;
            }
            return Math.max(depth(root.left), depth(root.right)) + 1;
        }

        // Decodes your encoded data to tree.
        @Override
        public TreeNode deserialize(String data) {
            if (data == null) {
                return null;
            }
            String[] strings = data.split(",");
            int length = strings.length;
            int lastRoot = 0;
            if (length >= 2) {
                lastRoot = length / 2 - 1;
            }
            TreeNode[] nodes = new TreeNode[lastRoot + 1];
            String first = strings[0];
            if (!first.equals("null")) {
                nodes[0] = new TreeNode(Integer.valueOf(first));
            }

            if (length == 1) {
                return nodes[0];
            }

            for (int i = 0; i <= lastRoot; i++) {
                TreeNode root = nodes[i];
                if (root != null) {
                    int left = 2 * i + 1;
                    int right = 2 * i + 2;
                    String leftStr = strings[left];
                    String rightStr = strings[right];

                    if (!leftStr.equals("null")) {
                        TreeNode leftNode = new TreeNode(Integer.valueOf(leftStr));
                        root.left = leftNode;
                        if (left <= lastRoot) {//可能超出
                            nodes[left] = leftNode;
                        }
                    }

                    if (!rightStr.equals("null")) {
                        TreeNode rightNode = new TreeNode(Integer.valueOf(rightStr));
                        root.right = rightNode;
                        if (right <= lastRoot) {//可能超出
                            nodes[right] = rightNode;
                        }
                    }
                }
            }
            return nodes[0];
        }

    }

    /**
     * 深度遍历的解法：使用先序或者后序遍历，因为有null节点，所以不需要中序遍历也可以确定根节点，从而确定二叉树
     */
    private static class DFSTreeCodec implements ITreeCodec {
        @Override
        public String serialize(TreeNode root) {
            if (root == null) {
                return "null,";
            }
            return root.val + "," + serialize(root.left) + serialize(root.right);
        }

        @Override
        public TreeNode deserialize(String data) {
            String[] strings = data.split(",");
            Queue<String> queue = new ArrayDeque(Arrays.asList(strings));

            return buildTree(queue);
        }

        private TreeNode buildTree(Queue queue) {
            String str = (String) queue.poll();
            if (str == null || str.equals("null")) {
                return null;
            }
            Integer integer = Integer.valueOf(str);
            TreeNode root = new TreeNode(integer);
            root.left = buildTree(queue);
            root.right = buildTree(queue);
            return root;
        }
    }
}
