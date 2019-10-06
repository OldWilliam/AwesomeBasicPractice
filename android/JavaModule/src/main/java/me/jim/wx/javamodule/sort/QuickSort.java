package me.jim.wx.javamodule.sort;

import java.util.Locale;

/**
 * 思想：冒泡排序扫描一趟最多才调整一个逆序，可以想法让一趟多调整些逆序数字
 * 方法；找个数，小的放这数前面，大的放后面，就可以一趟调整多个逆序了
 * 关键词：递归、枢轴、子序列
 */
class QuickSort implements SortMain.ISort {


    @Override
    public void sort(int[] array) {
        innerSort(array, 0, array.length - 1);
    }

    private void innerSort(int[] array, int left, int right) {
        if (left < right) {//不是while，递归的退出条件，不可再分子列表
            int pivot = QuickPass(array, left, right);
            innerSort(array, left, pivot - 1);
            innerSort(array, pivot + 1, right);
        }


    }

    int QuickPass(int[] array, int left, int right) {
        int low = left;
        int high = right;
        int pivot = array[low];

        while (low < high) {
            while (array[high] > pivot && high > low) {
                high--;
            }
            if (low < high) {//调整完成了，应当退出，不然会影响后面逻辑
                array[low] = array[high];
                low++;
            } else {
                break;
            }

            while (array[low] < pivot && high > low) {
                low++;
            }
            if (low < high) {
                array[high] = array[low];
                high--;
            } else {
                break;
            }
        }
        array[low] = pivot;
        for (int anArray : array) {
            System.out.print(String.format(Locale.US, "%2d ", anArray));
        }
        System.out.println();
        return low;
    }
}
