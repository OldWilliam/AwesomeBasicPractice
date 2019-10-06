package me.jim.wx.javamodule.concurrent;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Date: 2019/6/17
 * Name: wx
 * Description: 线程安全的计数器
 */
public class Counter {
    private int i = 0;
    private AtomicInteger atomI = new AtomicInteger(0);

    public static void main(String[] args) {
        ArrayList<Thread> threads = new ArrayList<>();
        final Counter counter = new Counter();
        for (int i = 0; i < 1000; i++) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        counter.count();
                        counter.safeCount();
                    }
                }
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(counter.i + " " +counter.atomI.get());
    }

    private void safeCount() {
        while (true) {
            int i = atomI.get();
            boolean success = atomI.compareAndSet(i, ++i);
            if (success) {
                break;
            }
        }
    }

    private void count() {
        i++;
    }
}
