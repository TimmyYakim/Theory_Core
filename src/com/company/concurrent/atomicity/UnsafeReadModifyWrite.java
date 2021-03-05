package com.company.concurrent.atomicity;

/**
 * Операция инкремента не атомарна. Запустив на многоядерном процессоре получаем меньше 1000.
 */
public class UnsafeReadModifyWrite {

    private int number;

    public void incrementNumber() {
        number++;
    }

    public int getNumber() {
        return this.number;
    }

    public static void main(String[] args) throws InterruptedException {
        final UnsafeReadModifyWrite rwm = new UnsafeReadModifyWrite();
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
//9999
//10000