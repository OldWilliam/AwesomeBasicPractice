package me.jim.wx.javamodule.sort;

/**
 * Date: 2019/7/2
 * Name: wx
 * Description:
 */
class BubbleSort implements SortMain.ISort {
    @Override
    public void sort(int[] array) {
        //外层遍历只需n-1次
        for (int i = 0; i < array.length - 1; i++) {
            //小心越界
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(j, j + 1, array);
                }
            }
        }
    }
}
