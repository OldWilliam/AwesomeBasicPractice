package me.jim.wx.javamodule.SwordRefersToOffer;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Date: 2019/8/13
 * Name: wx
 * Description:
 * 如何得到一个数据流中的中位数？
 * 如果从数据流中读出奇数个数值，
 * 那么中位数就是所有数值排序之后位于中间的数值。
 * 如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。
 * 我们使用Insert()方法读取数据流，使用GetMedian()方法获取当前读取数据的中位数。
 */
public class MiddleNumOfStream {

    public static void main(String[] args) {
        int[] num = new int[]{5, 2, 3, 4, 1, 6, 7, 0, 8};
        MiddleNumOfStream stream = new MiddleNumOfStream();
        for (int n : num) {
            stream.Insert(n);
            System.out.println(stream.GetMedian());
        }

    }

    //大顶堆
    private Queue<Integer> left = new PriorityQueue<>((o1, o2) -> o2 - o1);
    //小顶堆
    private Queue<Integer> right = new PriorityQueue<>();


    //读取数据流
    public void Insert(Integer num) {
        if (left.size() <= right.size()) {
            right.offer(num);
            left.offer(right.poll());
        } else {
            left.offer(num);
            right.offer(left.poll());
        }
    }

    //获取中位数
    public Double GetMedian() {
        int diff = left.size() - right.size();
        if (diff > 0) {
            return Double.valueOf(left.peek());
        }

        if (diff < 0) {
            return Double.valueOf(right.peek());
        }
        return (left.peek() + right.peek()) / 2.0;
    }
}
