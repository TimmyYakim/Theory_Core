package com.company.concurrent.atomicity;

/**
 * synchronized гарантирует вхождение в метод только одного потока за единицу времени
 */
public class SafeCheckThenAct {

    private int number;

    public synchronized void changeNumber() {
        if (number == 0) {
            System.out.println(Thread.currentThread().getName() + " | Changed");
            number = -1;
        } else {
            System.out.println(Thread.currentThread().getName() + " | Not changed");
        }
    }

    public static void main(String[] args) {
        SafeCheckThenAct checkAct = new SafeCheckThenAct();
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

//        Стабильно
//        T0 | Changed
//        T1 | Not changed
//        T2 | Not changed
//        T3 | Not changed