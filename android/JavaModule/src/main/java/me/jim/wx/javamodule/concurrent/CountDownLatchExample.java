package me.jim.wx.javamodule.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Date: 2019-09-19
 * Name: wx
 * Description:
 */
public class CountDownLatchExample {
    public static void main(String[] args) {

        //对比CyclicBarrier只有一个await来说，它有countDown和await方法
        //这表示一个或者多个线程可以等待，其他一组线程执行完成调用countDown
        CountDownLatch latch = new CountDownLatch(2);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                latch.countDown();
                latch.countDown();
            }
        });
        thread.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("One Open Gate");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Two Open Gate");
            }
        }).start();
    }
}
