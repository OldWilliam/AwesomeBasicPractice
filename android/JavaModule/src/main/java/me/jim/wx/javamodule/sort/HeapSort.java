package me.jim.wx.javamodule.sort;

import java.util.Locale;

/**
 * Date: 2019/7/2
 * Name: wx
 * Description:
 *
 * 1、每个子堆都是大顶堆
 */
public class HeapSort implements SortMain.ISort {
    @Override
    public void sort(int[] array) {

        createHeap(array);
        for (int len = array.length - 1; len >= 1; len--) {
            swap(0, len, array);
            adjustHeap(array, len, 0);
            for (int anArray : array) {
                System.out.print(String.format(Locale.US, "%2d ", anArray));
            }
            System.out.println();
        }
    }

    //不仅要把大的调上去，小的也要调下去，保证每个子堆是大根堆
    private void adjustHeap(int[] array, int len, int root) {
        int lastRoot = len / 2 - 1;
        while (root <= lastRoot) {
            int left = root * 2 + 1;
            int right = root * 2 + 2;
            int largest = root;

            if (right <= len - 1) {
                if (array[right] > array[largest]) {
                    largest = right;
                }
            }

            if (left <= len - 1) {
                if (array[left] > array[largest]) {
                    largest = left;
                }
            }

            if (root != largest) {
                swap(largest, root, array);
                root = largest;
            } else {
                break;
            }
        }
    }

    private void createHeap(int[] array) {
        for (int root = array.length / 2 - 1; root >= 0; root--) {
            adjustHeap(array, array.length, root);
        }
    }
}
