package me.jim.wx.javamodule.sort;

import java.util.Locale;
import java.util.Random;

/**
 * Date: 2019/6/20
 * Name: wx
 * Description: 快排
 */
public class SortMain {
    public static void main(String[] args) {
        int count = 20;
        int[] input = new int[count];
        for (int i = 0; i < count; i++) {
            int anInt = new Random().nextInt(100);
            input[i] = anInt;
        }
//        input = new int[]{35, 23, 68, 22, 47, 0, 94, 56, 67, 10};
        System.out.println("输入:");
        for (int anInput : input) {
            System.out.print(String.format(Locale.US, "%2d ", anInput));
        }
        System.out.println("\n");

        Sort sort = new HeapSort();
        sort.sort(input);

        System.out.println("\n输出：");
        for (int anInput : input) {
            System.out.print(String.format(Locale.US, "%2d ", anInput));
        }
    }

    interface Sort {
        void sort(int[] array);

        default void swap(int i, int j, int[] array) {
            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }


}
