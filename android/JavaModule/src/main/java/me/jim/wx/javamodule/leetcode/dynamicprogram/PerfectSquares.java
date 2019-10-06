package me.jim.wx.javamodule.leetcode.dynamicprogram;

import java.util.ArrayList;

/**
 * Date: 2019/7/2
 * Name: wx
 * Description:
 */
public class PerfectSquares {
    public static void main(String[] args) {
        int numSquares = new PerfectSquares().numSquares(12);
        System.out.println(numSquares);
    }
    public int numSquares(int n) {
        int i = 0;
        int square = i * i;

        ArrayList<Integer> squares = new ArrayList<>();
        while (square <= n) {
            squares.add(square);
            i++;
            square = i * i;
        }

        int[] res = new int[n + 1];
        for (int j = 1; j <= n; j++) {
            res[j] = getMinRes(res, squares, j);
        }
        return res[n];
    }

    private int getMinRes(int[] res, ArrayList<Integer> squares, int j) {
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < squares.size(); i++) {
            Integer square = squares.get(i);
            if (square <= j) {
                min = Math.min(min, res[j - square] + 1);
            } else {
                break;
            }
        }
        return min;
    }
}
