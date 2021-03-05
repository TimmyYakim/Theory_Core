package com.company.concurrent.atomicity;

import java.util.concurrent.CountDownLatch;

/**
 * Поле number должно увеличиваться на 1 каждый раз для одного потока.
 * Однако из-за наличия гонки некоторые обновления могут быть потеряны.
 * Гонка более выражена по причине одновременного старта всех потоков startSignal.countDown
 */
public class UnsafeReadModifyWriteWithLatch {

    private static final int NUM_THREADS = 1000;
    private int number;
    private final CountDownLatch startSignal = new CountDownLatch(1);
    private final CountDownLatch endSignal = new CountDownLatch(NUM_THREADS);

    public void incrementNumber() {
        number++;
    }

    public int getNumber() {
        return this.number;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i=0; i<100; i++) {
            test();
        }
    }


    private static void test() throws InterruptedException {
        final UnsafeReadModifyWriteWithLatch rwm = new UnsafeReadModifyWriteWithLatch();
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
        Thread.sleep(2000);
        rwm.startSignal.countDown(); // Всем стартовать
        rwm.endSignal.await(); // Ждем когда все потоки завершатся
        System.out.println(rwm.getNumber());

    }

}

//        999
//        1000
//        1000