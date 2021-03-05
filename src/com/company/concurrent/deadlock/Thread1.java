package com.company.concurrent.deadlock;

class Thread1 extends Thread {

    private Object a;
    private Object b;

    public Thread1(Object a, Object b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        synchronized (a) {
            System.out.println("Занят монитор a потоком 1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            synchronized (b) {
                System.out.println("Занят монитор b потоком 1");
            }
        }
    }
}