package me.jim.wx.javamodule.concurrent;

import java.util.Stack;

/**
 * Date: 2019/7/26
 * Name: wx
 * Description: 生产者消费者
 */
public class ProducerConsumer {

    public static void main(String[] args) {

        Stack<String> stack = new Stack<>();
        int seq = 0;

        Producer producer = new Producer(5, stack, seq);
        new Thread(() -> {
            try {
                producer.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                producer.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        Consumer consumer = new Consumer(stack);
        new Thread(() -> {
            try {
                consumer.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


    }

    static class Producer {
        private int capacity;
        private final Stack<String> stack;
        private int seq;

        Producer(int capacity, Stack<String> stack, int seq) {
            this.capacity = capacity;
            this.stack = stack;
            this.seq = seq;
        }

        void produce() throws InterruptedException {

            while (true) {
                Thread.sleep(1000);
                synchronized (stack) {
                    int size = stack.size();

                    while (size == capacity) {
                        stack.wait();
                    }
                    if (size < capacity) {
                        stack.push("nothing" + seq++);
                        stack.notify();
                    }
                }
            }
        }
    }

    static class Consumer {

        private final Stack<String> stack;

        Consumer(Stack<String> stack) {
            this.stack = stack;
        }

        void consume() throws InterruptedException {
            while (true) {
                synchronized (stack) {
                    while (stack.size() == 0) {
                        stack.wait();
                    }
                    String str = stack.pop();
                    System.out.println(str);
                    stack.notify();
                }
            }
        }
    }
}
