package me.jim.wx.javamodule.leetcode;

import java.util.HashMap;

/**
 * Date: 2019/6/27
 * Name: wx
 * Description: https://leetcode-cn.com/problems/lru-cache/
 *
 * @LinkedHashMap 就是实现了这种功能
 */
public class LRUCache {

    private HashMap<Integer, Node> map = new HashMap<>();
    private Node head, tail;
    private int size = 0;
    private int capacity;

    public LRUCache(int capacity) {

        this.capacity = capacity;

    }

    public static void main(String[] args) {

        //["LRUCache","get","put","get","put","put","get","get"]
        //[[2],[2],[2,6],[1],[1,5],[1,2],[1],[2]]
        //预期：[null,-1,null,-1,null,null,2,6]

        //["LRUCache","put","put","get","get","put","get","get","get"]
        //[[2],[2,1],[3,2],[3],[2],[4,3],[2],[3],[4]]
        //[null,null,null,2,1,null,1,-1,3]

        LRUCache lruCache = new LRUCache(2);
        lruCache.put(2, 1);
        lruCache.put(3, 2);
        System.out.println(lruCache.get(3));
        System.out.println(lruCache.get(2));
        lruCache.put(4, 3);
        System.out.println(lruCache.get(2));
        System.out.println(lruCache.get(3));
        System.out.println(lruCache.get(4));

    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;//缓存未命中
        }
        Node prev = node.prev;
        Node next = node.next;

        if (tail != node) { //直接挪到链尾
            if (prev != null) {
                prev.next = next;
            } else {
                head = next;
            }

            if (next != null) {
                next.prev = prev;
            } else {
                tail = prev;
            }

            if (tail == null) {
                head = node;
            } else {
                tail.next = node;
                node.prev = tail;
            }
            tail = node;
        }

        return node.val;
    }

    public void put(int key, int value) {
        if (get(key) != -1) {//缓存也可能会更新的
            map.get(key).val = value;
            return;
        }
        Node node = new Node(key, value);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
        if (size > capacity) {//缓存失效
            map.remove(head.key);
            head = head.next;
            if (head != null && head.prev != null) {
                head.prev.next = null;
                head.prev = null;
            }
            size--;
        }
        map.put(key, node);
    }

    private class Node {
        int val;
        int key;
        Node next;
        Node prev;
        int count;

        Node(int key, int val) {

            count = 0;
            this.key = key;
            this.val = val;
            next = null;
            prev = null;
        }
    }
}
