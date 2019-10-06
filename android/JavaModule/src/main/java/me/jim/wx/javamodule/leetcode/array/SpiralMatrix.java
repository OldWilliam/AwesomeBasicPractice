package me.jim.wx.javamodule.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Date: 2019/6/24
 * Name: wx
 * Description:
 * <p>
 * 给定一个包含 m x n 个元素的矩阵（m 行, n 列），请按照顺时针螺旋顺序，返回矩阵中的所有元素。
 * <p>
 * 示例 1:
 * <p>
 * 输入:
 * [
 * [ 1, 2, 3 ],
 * [ 4, 5, 6 ],
 * [ 7, 8, 9 ]
 * ]
 * 输出: [1,2,3,6,9,8,7,4,5]
 * 示例 2:
 * <p>
 * 输入:
 * [
 * [1, 2, 3, 4],
 * [5, 6, 7, 8],
 * [9,10,11,12]
 * ]
 * 输出: [1,2,3,4,8,12,11,10,9,5,6,7]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/spiral-matrix
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class SpiralMatrix {

    public static void main(String[] args) {
        int[][] matrix = {{2}, {3}};
        System.out.println(matrix[1][0]);
        new SpiralMatrix().spiralOrder(matrix);
    }

    /**
     * m, n++
     * m++, n
     * m, n--
     * m--, n
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null) {
            return null;
        }
        if (matrix.length == 0 || matrix[0].length == 0) {
            return new ArrayList<>();
        }
        int size = matrix.length * matrix[0].length;
        ArrayList<Integer> integers = new ArrayList<>(size);
        if (matrix.length == 1) {
            for (int i : matrix[0]) {
                integers.add(i);
            }
            return integers;
        }
        if (matrix[0].length == 1) {
            for (int[] aMatrix : matrix) {
                integers.add(aMatrix[0]);
            }
            return integers;
        }
        int mi = 0;
        int ni = 0;
        int m = matrix.length;
        int n = matrix[0].length;
        int flag = 1;
        boolean[][] seen = new boolean[m][n]; //关键
        for (int i = 0; i < size; i++) {
            integers.add(matrix[mi][ni]);
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
                    if (mi >= m - 1 || seen[mi + 1][ni]) {
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
        return integers;
    }
}
