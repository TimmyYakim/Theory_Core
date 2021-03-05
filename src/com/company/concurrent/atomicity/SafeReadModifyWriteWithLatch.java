package com.company.concurrent.atomicity;

import java.util.concurrent.CountDownLatch;

public class SafeReadModifyWriteWithLatch {
    private static final int NUM_THREADS = 1000;
    private int number;
    private final CountDownLatch startSignal = new CountDownLatch(1);
    private final CountDownLatch endSignal = new CountDownLatch(NUM_THREADS);

    public synchronized void incrementNumber() {
        number++;
    }

    public synchronized int getNumber() {
        return this.number;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i=0; i<100; i++) {
            test();
        }
    }


    private static void test() throws InterruptedException {
        final SafeReadModifyWriteWithLatch rwm = new SafeReadModifyWriteWithLatch();
        for (int i=0; i<NUM_THREADS; i++) { // создаем потоки
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        rwm.startSignal.await(); // каждый из которых паркуется в ожидании startSignal
                        rwm.incrementNumber();
                    } catch (InterruptedException e) {

                    } finally {
                        rwm.endSignal.countDown(); // по окончании каждого оповестиь счетчик
                    }
                }
            }, "T"+i).start();
        }
//        Thread.sleep(2000);
        rwm.startSignal.countDown(); // Всем стартовать
        rwm.endSignal.await(); // Ждем когда все потоки завершатся
        System.out.println(rwm.getNumber());

    }
}


// 1000 всегда