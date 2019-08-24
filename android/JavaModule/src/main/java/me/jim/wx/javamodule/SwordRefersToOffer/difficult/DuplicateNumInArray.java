package me.jim.wx.javamodule.SwordRefersToOffer.difficult;

/**
 * Date: 2019/8/12
 * Name: wx
 * Description:
 * <p>
 * 在一个长度为n的数组里的所有数字都在0到n-1的范围内。 数组中某些数字是重复的，
 * 但不知道有几个数字是重复的。也不知道每个数字重复几次。请找出数组中任意一个重复的数字。
 * 例如，如果输入长度为7的数组{2,3,1,0,2,5,3}，那么对应的输出是第一个重复的数字2。
 */
public class DuplicateNumInArray {

    public static void main(String[] args) {
        int[] numbers = new int[]{2, 3, 1, 0, 2, 5, 3};
        int[] res = new int[]{-1};

        new DuplicateNumInArray().duplicate(numbers, numbers.length, res);
        System.out.println(res[0]);
    }

    private boolean duplicate(int[] numbers, int length, int[] duplication) {
        for (int i = 0; i < length; i++) {
            while (i != numbers[i]) { //0、1、2、3、2、5、3 这个时候进入死循环
                if (numbers[i] == numbers[numbers[i]]) {
                    duplication[0] = numbers[i];
                    return true;
                }
                swap(numbers, i, numbers[i]);
            }
        }

        return false;
    }

    //wrong answer
    public boolean duplicate1(int numbers[], int length, int[] duplication) {
        for (int i = 0; i < length; i++) {
            while (i != numbers[i]) { //0、1、2、3、2、5、3 这个时候进入死循环
                swap(numbers, i, numbers[i]);
            }

            if (numbers[i] == numbers[numbers[i]] && i != numbers[i]) {
                duplication[0] = numbers[i];
                break;
            }
        }

        return duplication[0] == -1;
    }

    private void swap(int[] numbers, int i, int j) {
        int tmp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = tmp;
    }
}
