package com.company.concurrent.synchronizer;

public class Incrementator extends Thread {

    private Monitor monitor;

    public Incrementator(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        for (int i=0; i<10000; i++) {
            monitor.increment();
        }
    }
}
