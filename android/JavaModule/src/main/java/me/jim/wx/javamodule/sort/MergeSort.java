package me.jim.wx.javamodule.sort;

/**
 * Date: 2019/8/14
 * Name: wx
 * Description:
 */
public class MergeSort implements SortMain.ISort {
    private int[] tmp;
    @Override
    public void sort(int[] array) {
        tmp = new int[array.length];
        Msort(array, 0, array.length - 1, array);
    }

    private void Msort(int[] array, int low, int high, int[] result) {
        if (low == high) {
            result[low] = array[low];
            return;
        }
        int mid = (low + high) / 2;
        Msort(array, low, mid, tmp);
        Msort(array, mid + 1, high, tmp);
        Merge(tmp, low, mid, high, result);
    }

    private void Merge(int[] array, int low, int mid, int high, int[] result) {

    }
}
