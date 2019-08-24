package me.jim.wx.javamodule.SwordRefersToOffer;

/**
 * Date: 2019/8/13
 * Name: wx
 * Description:
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，
 * 使得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分，
 * 并保证奇数和奇数，偶数和偶数之间的相对位置不变。
 * <p>
 * 1、2、3、4、5、6
 */
public class ReOrder {
    public static void main(String[] args) {
        int[] array = new int[]{};
        new ReOrder().reOrderArray(array);
        for (int i : array) {
            System.out.print(i);
        }
    }
    public void reOrderArray(int[] array) {
        int it = 0;
        int start = -1;
        while (it < array.length) {
            while (it < array.length && array[it] % 2 == 1) {
                it++;
            }
            if (it >= array.length) {
                break;
            }
            if (start == -1) {
                start = it;
            }
            while (it < array.length && array[it] % 2 == 0) {
                it++;
            }
            if (it >= array.length) {
                break;
            }
            int end = it;
            int tmp = array[it];
            for (int i = end; i > start; i--) {
                array[i] = array[i - 1];
            }
            array[start] = tmp;
            start++;
        }
    }
}
