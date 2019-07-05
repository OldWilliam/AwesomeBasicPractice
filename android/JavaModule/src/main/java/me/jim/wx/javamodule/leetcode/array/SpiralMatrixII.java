package me.jim.wx.javamodule.leetcode.array;

/**
 * Date: 2019/6/24
 * Name: wx
 * Description:
 */
public class SpiralMatrixII {
    public int[][] generateMatrix(int n) {
        if (n == 0) {
            return null;
        }
        if (n == 1) {
            return new int[][]{{1}};
        }
        double size = n * n;
        int[][] matrix = new int[n][n];

        int mi = 0;
        int ni = 0;
        int flag = 1;
        boolean[][] seen = new boolean[n][n]; //关键
        for (int i = 0; i < size; i++) {
            matrix[mi][ni] = i + 1;
            seen[mi][ni] = true;
            switch (flag) {
                case 1:
                    ni++;
                    if (ni >= n - 1 || seen[mi][ni + 1]) {
                        flag = 2;
                    }
                    break;
                case 2:
                    mi++;
                    if (mi >= n - 1 || seen[mi + 1][ni]) {
                        flag = 3;
                    }
                    break;
                case 3:
                    ni--;
                    if (ni <= 0 || seen[mi][ni - 1]) {
                        flag = 4;
                    }
                    break;
                case 4:
                    mi--;
                    if (mi <= 0 || seen[mi - 1][ni]) {
                        flag = 1;
                    }
                    break;
                default:
                    break;
            }
        }
        return matrix;
    }
}
