package me.jim.wx.javamodule.SwordRefersToOffer;

import java.util.Stack;

/**
 * Date: 2019/8/11
 * Name: wx
 * Description:
 */
public class StackWithFunMin {

    private Stack<Integer> dataStack = new Stack<>();
    private Stack<Integer> minStack = new Stack<>();
    private int min = Integer.MAX_VALUE;

    public void push(int node) {
        dataStack.push(node);
        if (node < min) {
            min = node;
        }
        minStack.push(min);
    }

    public void pop() {
        dataStack.pop();
        minStack.pop();
    }

    public int top() {
        return dataStack.peek();
    }

    public int min() {
        return minStack.peek();
    }
}
