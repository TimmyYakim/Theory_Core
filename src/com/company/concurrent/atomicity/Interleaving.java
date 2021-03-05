package com.company.concurrent.atomicity;

/**
 * Последовательность потоков не гарантируется
 */
public class Interleaving {

    public void show() {
        for (int i=0; i<5; i++) {
            System.out.println(Thread.currentThread().getName() + " - Number: " + i);
        }
    }

    public static void main(String[] args) {
        final Interleaving main = new Interleaving();
        Runnable runner = new Runnable() {
            @Override
            public void run() {
                main.show();
            }
        };
        new Thread(runner, "Thread1").start();
        new Thread(runner, "Thread2").start();
    }


}

//        Thread1 - Number: 0
//        Thread2 - Number: 0
//        Thread1 - Number: 1
//        Thread2 - Number: 1
//        Thread2 - Number: 2
//        Thread2 - Number: 3
//        Thread2 - Number: 4
//        Thread1 - Number: 2
//        Thread1 - Number: 3
//        Thread1 - Number: 4
