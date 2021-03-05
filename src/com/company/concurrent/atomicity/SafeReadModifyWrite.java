package com.company.concurrent.atomicity;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger потокобезопасен. Гонка потоков не возникает.
 */
public class SafeReadModifyWrite {


    private AtomicInteger number = new AtomicInteger();

    public void incrementNumber() {
        number.getAndIncrement();
    }

    public int getNumber() {
        return this.number.get();
    }

    public static void main(String[] args) throws InterruptedException {
        final SafeReadModifyWrite rwm = new SafeReadModifyWrite();
        for (int i=0; i<10000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    rwm.incrementNumber();
                }
            }, "T"+i).start();
        }
        Thread.sleep(10000);
        System.out.println(rwm.getNumber());
    }


}
