package me.jim.wx.javamodule.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Date: 2019-09-18
 * Name: wx
 * Description:
 */
public class CyclicBarrierExample {
    public static void main(String[] args) {

        //循环栅栏，只有await方法，当await被调用预期数量时候，栅栏打开
        int parties = 3;
        CyclicBarrier barrier = new CyclicBarrier(parties);

        for (int i = 0; i < parties; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Barrier is broken");
                }
            }).start();
        }
    }
}
