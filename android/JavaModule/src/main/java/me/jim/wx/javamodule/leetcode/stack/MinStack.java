package me.jim.wx.javamodule.leetcode.stack;

import java.util.LinkedList;

/**
 * Date: 2019/6/21
 * Name: wx
 * Description:
 */
public class MinStack {
    private LinkedList<Integer> stack = new LinkedList<>();
    private int min = Integer.MAX_VALUE;
    public MinStack() {

    }

    public void push(int x) {
        stack.push(x);
        min = Math.min(min, x);
    }

    public void pop() {
        stack.pop();
        min = Integer.MAX_VALUE;
        for (Integer integer : stack) {
            min = Math.min(min, integer);
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return min;
    }
}
