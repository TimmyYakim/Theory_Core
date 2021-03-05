package com.company.concurrent.multithread;

public class Main {

    public static void main(String[] args) {
        CounterThread counterThread = new CounterThread();
        Thread thread = new Thread(counterThread);
        thread.start();
        for (int i=0; i<590_000; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }

    }


}
//        Thread-0 65762
//        Thread-0 65763
//        Thread-0 65764
//        Thread-0 65765
//        Thread-0 65766
//        main 48055
//        Thread-0 65767
//        Thread-0 65768
// ...