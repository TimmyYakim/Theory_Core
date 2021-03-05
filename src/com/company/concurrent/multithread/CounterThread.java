package com.company.concurrent.multithread;


public class CounterThread implements Runnable {
    @Override
    public void run() {
        long res = 0;
        for (int i=0; i<600_000; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
            res++;
        }
        System.out.println(res);
    }
}
