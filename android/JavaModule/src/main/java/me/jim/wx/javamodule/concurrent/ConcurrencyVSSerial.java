package me.jim.wx.javamodule.concurrent;

/**
 * 并行vs串行处理速度
 */
public class ConcurrencyVSSerial {
    private static final int count = 1000000;

    public static void main(String[] args) throws InterruptedException {
        concurrency();
        serial();
    }

    private static void serial() {
        final int[] a = {10};
        int b = -10;
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            a[0]++;
        }
        for (int i = 0; i < count; i++) {
            b--;
        }
        System.out.println("串行：" + (System.currentTimeMillis() - start) + " " + a[0] + " " + b);
    }

    private static void concurrency() throws InterruptedException {
        final int[] a = {10};
        int b = -10;
        long start = System.currentTimeMillis();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < count; i++) {
                    a[0]++;
                }
            }
        });
        thread.start();

        for (int i = 0; i < count; i++) {
            b--;
        }

        thread.join();

        System.out.println("并行：" + (System.currentTimeMillis() - start) + " " + a[0] + " " + b);
    }
}
