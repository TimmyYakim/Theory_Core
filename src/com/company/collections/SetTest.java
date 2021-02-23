package com.company.collections;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

/**
 * Представляет собой неупорядоченную коллекцию, которая не может содержать дублирующиеся данные. Является программной моделью математического понятия «множество».
 */
public class SetTest {

    public static void main(String[] args) {
        testHashSet();
        testLinkedHashSet();
        testTreeSet();
    }


    /**
     * HashSet
     * В основе HashMap.
     * Ключ - добавляемый элемент, значение — объект-пустышка (new Object()).
     * Порядок элементов не гарантируется при добавлении.
     * Позволяет хранить любые значения в том числе и null;
     * Не синхронизирован.
     *
     * Поиск, вставка, удаление за O(1) лучшем случае, O(n) - в худшем
     */
    private static void testHashSet() {
        HashSet<Integer> hs = new HashSet<>();
        hs.add(6);
        hs.add(5);
        hs.add(2);
        hs.add(1);
        System.out.println(hs); // [1, 2, 5, 6]
    }

    /**
     * LinkedHashSet
     * В основе LinkedHashMap.
     * Entries соединены ссылками next, previous.
     * Благодаря этому отличию порядок элементов при обходе коллекции является идентичным порядку
     * добавления элементов.
     * Уникальность достигается тем что объекты в одном бакете сравниваются через equals().
     *
     * Поиск, вставка, удаление за O(1) лучшем случае, O(n) - в худшем
     */
    private static void testLinkedHashSet() {
        LinkedHashSet<Integer> lhs = new LinkedHashSet<>();
        lhs.add(8);
        lhs.add(4);
        lhs.add(8);
        lhs.add(5);
        System.out.println(lhs); // [8, 4, 5]
    }

    /**
     * TreeSet
     * В основе TreeMap
     * Предоставляет возможность управлять порядком элементов в коллекции (Comparator либо "natural ordering").
     *
     * Поиск, вставка, удаление за O(log(n))
     */
    private static void testTreeSet() {
        TreeSet<Integer> ts = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        ts.add(4);
        ts.add(1);
        ts.add(0);
        ts.add(14);
        System.out.println(ts); // [0, 1, 4, 14]
        ts.descendingIterator();
        ts.ceiling(14); // 14
        ts.floor(0); // null
        ts.higher(14); // 0
        ts.lower(0); // null
        ts.descendingSet(); // [14, 4, 1, 0]
        ts.first(); // 0
        ts.last(); // 14
        ts.headSet(3); // [0, 1]
        ts.tailSet(3); // [4, 14]
//        ts.pollFirst();
//        ts.pollLast();
//        ts.subSet();
    }


}
