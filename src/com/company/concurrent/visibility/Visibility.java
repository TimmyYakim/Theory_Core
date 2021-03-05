package com.company.concurrent.visibility;

/**
 * volatite гарантирует (happens-before)
 *
 */
public class Visibility {
    private static volatile boolean ready;

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (ready) {
                        System.out.println("Flag changed received");
                        break;
                    }
                }
            }
        }).start();
        Thread.sleep(3000);
        System.out.println("Changing flag");
        ready = true;

    }

}