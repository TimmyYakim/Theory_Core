package com.company.concurrent.synchronizer;

/**
 * Демонстрация семофора
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Monitor monitor = new Monitor();
        Incrementator thread1 = new Incrementator(monitor);
        Incrementator thread2 = new Incrementator(monitor);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(monitor.getStore());
    }

}

// 20000