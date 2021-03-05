package com.company.concurrent.waitNotify;

/**
 * wait notify
 */

public class Main {

    public static void main(String[] args) {
        Store store = new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);
        new Thread(producer).start();
        new Thread(consumer).start();
        new Thread(producer).start();
        new Thread(consumer).start();
    }


}

//        1 good more on the store
//        1 goods on the store
//        buyer bought 1 good
//        0 goods on the store
//        wait for put
//        1 good more on the store
//        1 goods on the store
//        1 good more on the store
//        2 goods on the store