package me.jim.wx.javamodule.concurrent;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * Date: 2019/8/16
 * Name: wx
 * Description:
 */
public class Main {
    static final Semaphore semaphore = new Semaphore(3);


    public static void main(String[] args) throws InterruptedException {
        LinkedList<Object> objects = new LinkedList<>();
        objects.add(1);
        objects.remove("");

        synchronized (semaphore) {
            System.out.println("Before");
            objects.wait();
            System.out.println("After");

        }

        new Stub2();
    }


    static class Stub {
        public String name = "Stub";

        public Stub() {
            sayName();
        }

        public void sayName() {
            System.out.println(name);
        }
    }


    static class Stub2 extends Stub{
        public  String name = "Stub2";

        @Override
        public void sayName() {
            System.out.println(name);
        }
    }
}
