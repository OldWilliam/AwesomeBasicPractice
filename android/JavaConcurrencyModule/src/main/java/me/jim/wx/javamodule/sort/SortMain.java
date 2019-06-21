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

        Sort sort = new QuickSort();
        sort.sort(input);

        for (int i = 0; i < input.length; i++) {
            System.out.print(String.format(Locale.US, "%2d ", input[i]));
            if ((i + 1) % 10 == 0) {
//                System.out.println();
            }
        }
    }

    interface Sort {
        void sort(int[] array);
    }


    private static class BubbleSort implements Sort {
        @Override
        public void sort(int[] array) {
            //外层遍历只需n-1次
            for (int i = 0; i < array.length - 1; i++) {
                //小心越界
                for (int j = 0; j < array.length - i - 1; j++) {
                    if (array[j] > array[j + 1]) {
                        int tmp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = tmp;
                    }
                }
            }
        }
    }

    /**
     * 思想：冒泡排序扫描一趟最多才调整一个逆序，可以想法让一趟多调整些逆序数字
     * 方法；找个数，小的放这数前面，大的放后面，就可以一趟调整多个逆序了
     * 关键词：递归、枢轴、子序列
     */
    private static class QuickSort implements Sort {
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
                if (low == high) {//调整完成了，应当退出，不然会影响后面逻辑
                    break;
                }
                array[low] = array[high];
                low++;
                while (array[low] < pivot && high > low) {
                    low++;
                }
                if (low == high) {
                    break;
                }
                array[high] = array[low];
                high--;
            }
            array[low] = pivot;
            for (int anArray : array) {
                System.out.print(String.format(Locale.US, "%2d ", anArray));
            }
            System.out.println();
            return low;
        }
    }
}
