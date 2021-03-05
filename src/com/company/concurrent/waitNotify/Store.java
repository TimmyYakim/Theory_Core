package com.company.concurrent.waitNotify;

public class Store {

    private int products = 0;

    public synchronized void get() {
        while (products < 1) {
            System.out.println("wait for put");
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        products--;
        System.out.println("buyer bought 1 good");
        System.out.println(products + " goods on the store");
        notify();
    }

    public synchronized void put() {
        while (products >= 5) {
            System.out.println("wait for get");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        products++;
        System.out.println("1 good more on the store");
        System.out.println(products + " goods on the store");
        notify();
    }


}
