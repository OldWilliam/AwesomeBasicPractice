package me.jim.wx.javamodule.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Date: 2019/6/27
 * Name: wx
 * Description:
 */
public class FizzBuzz {
    public static void main(String[] args) {
        List<String> strings = new FizzBuzz().fizzBuzz(15);
        for (String string : strings) {
            System.out.println(string);
        }

        int target = (int) (Math.pow(2, 31) - 1);
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i <= 31; i++) {
            stack.push((target >> i) & 1);
        }

        while (stack.size() > 0) {
            System.out.print(stack.pop());
        }
    }

    public List<String> fizzBuzz(int n) {
        final String a = "Fizz";
        final String b = "Buzz";
        final String ab = "FizzBuzz";
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int it = i + 1;
            if (it >= 15 && it % 15 == 0) {
                strings.add(ab);
            } else {
                if (it >= 3 && it % 3 == 0) {
                    strings.add(a);
                } else if (it >= 5 && it % 5 == 0) {
                    strings.add(b);
                } else {
                    strings.add(String.valueOf(it));
                }
            }
        }
        return strings;
    }
}
