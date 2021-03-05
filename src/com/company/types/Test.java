package com.company.types;

public class Test {


    public static void main(String[] args) {

        //медленно, выделяется новая память
        Integer i1 = new Integer(100);
        //быстро
        //все, кроме чисел с плавающей точкой, от Byte до Long имеют кеш
        // По умолчанию этот кеш содержит значения от -128 до 127
        // можно увеличить кеш для Integer через системное свойство «java.lang.Integer.IntegerCache.high»,
        // а так же через параметр виртуальной машины -XX:AutoBoxCacheMax=<size>.
        Integer i2 = Integer.valueOf(100);
    }


}
