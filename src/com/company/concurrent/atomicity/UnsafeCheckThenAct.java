package com.company.concurrent.atomicity;

/**
 * Гонка потоков
 */
public class UnsafeCheckThenAct {

    private int number;

    public void changeNumber() {
        if (number == 0) {
            System.out.println(Thread.currentThread().getName() + " | Changed");
            number = -1;
        } else {
            System.out.println(Thread.currentThread().getName() + " | Not changed");
        }
    }

    public static void main(String[] args) {
        UnsafeCheckThenAct checkAct = new UnsafeCheckThenAct();
        for (int i=0; i<50 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    checkAct.changeNumber();
                }
            }, "T" + i).start();
        }
    }


}
//        T0 | Changed
//        T1 | Changed
//        T2 | Not changed
//        T3 | Not changed
//        T4 | Not changed
//        T5 | Not changed