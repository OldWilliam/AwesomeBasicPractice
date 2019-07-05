package me.jim.wx.javamodule.leetcode;

import java.util.Stack;

/**
 * Date: 2019/6/24
 * Name: wx
 * Description:
 */
public class ValidParentheses {
    public static void main(String[] args) {
        boolean valid = new ValidParentheses().isValid("{ []}");
        System.out.println(valid);
    }
    private boolean isValid(String s) {
        if (s == null) {
            return false;
        }
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char charAt = s.charAt(i);
            int value = getResult(charAt);
            if (stack.size() > 0 && stack.peek() + value == 0) {
                stack.pop();
            } else if (value != 0) {
                stack.push(value);
            }
        }
        return stack.size() == 0;
    }

    private int getResult(char charAt) {
        switch (charAt) {
            case '(':
                return 1;
            case ')':
                return -1;
            case '{':
                return 2;
            case '}':
                return -2;
            case '[':
                return 3;
            case ']':
                return -3;
            case ' ':
                return 0;
            default:
                break;

        }
        return -1;
    }
}
