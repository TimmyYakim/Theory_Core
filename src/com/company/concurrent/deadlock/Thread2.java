package com.company.concurrent.deadlock;

class Thread2 extends Thread {
    private Object a;
    private Object b;

    public Thread2(Object a, Object b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        synchronized (b) {
            System.out.println("Занят монитор b потоком 2");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            synchronized (a) {
                System.out.println("Занят монитор a потоком 2");
            }
        }
    }
}
