package me.jim.wx.javamodule.SwordRefersToOffer;

/**
 * Date: 2019/8/12
 * Name: wx
 * Description: 旋转数组最小值
 */
public class MinNumInRotateArray {

    //遍历一遍O（n）
    public int minNumberInRotateArray(int [] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int minValue  = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
                minIndex = i;
            }
        }
        return array[minIndex];
    }
}
