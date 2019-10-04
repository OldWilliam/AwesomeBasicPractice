package me.jim.wx.javamodule.concurrent;

/**
 * Date: 2019-09-18
 * Name: wx
 * Description:
 */
public class JoinExample {
    /**
     * 未加join
     * Thread
     * Main
     *
     * 加了join
     * Main
     * Thread
     */
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("I'm Thread");
            }
        });
        thread.start();
        thread.join();//Waits for this thread to die 会等待调用这个join方法的线程到执行完，生命结束
        System.out.println("I'm Main");
    }
}
