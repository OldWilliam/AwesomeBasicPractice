package me.jim.wx.javamodule.concurrent;

/**
 * Date: 2019-09-18
 * Name: wx
 * Description: 有一个数组，存有0～19，使用3个线程轮流顺序打印，同时只能有一个线程
 */
public class SequencePrintArrayByMultiThread {

    private static int index = 0;

    public static void main(String[] args) {
        int[] nums = new int[10000];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i;
        }
        Object mutex = new Object();

        Thread thread0 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (mutex) {
                    while (index < nums.length) {
                        if (index % 3 == 0) {
                            System.out.println(nums[index++]);
                            mutex.notifyAll();
                        } else {
                            try {
                                mutex.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (mutex) {
                    while (index < nums.length) {
                        if (index % 3 == 1) {
                            System.out.println(nums[index++]);
                            mutex.notifyAll();
                        } else {
                            try {
                                mutex.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (mutex) {
                    while (index < nums.length) {
                        if (index % 3 == 2) {
                            System.out.println(nums[index++]);
                            mutex.notifyAll();
                        } else {
                            try {
                                mutex.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        thread0.start();
        thread1.start();
        thread2.start();
    }
}

