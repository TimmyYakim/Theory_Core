package com.company.concurrent.race;

public class Counter {

    private int c;

    public Counter() {
        this.c = 0;
    }

    public Counter(int c) {
        this.c = c;
    }

    public /*synchronized*/ void increment() {
        c++;
    }

    public /*synchronized*/ int getCount() {
        return this.c;
    }

}
