package com.company.collections;

import java.util.*;

/**
 * Этот интерфейс описывает коллекции с предопределённым способом вставки и извлечения элементов, а именно — очереди FIFO (first-in-first-out). Помимо методов, определённых в интерфейсе Collection, определяет дополнительные методы для извлечения и добавления элементов в очередь. Большинство реализаций данного интерфейса находится в пакете java.util.concurrent и подробно рассматриваются в данном обзоре.
 */
public class QueueTest {

    public static void main(String[] args) {
        testPriorityQueue();

    }


    /**
     * PriorityQueue
     * Реализована на основе "кучи". Само же хранилище для этого — обычный массив.
     * Особенностью данной очереди является возможность управления порядком элементов.
     * По-умолчанию, элементы сортируются с использованием «natural ordering», но это поведение может быть
     * переопределено при помощи объекта Comparator, который задаётся при создании очереди.
     * Не поддерживает null в качестве элементов.
     * Нельзя использовать "non-comparable objects" (т.е. д.б. Comparator или comparable).
     *
     * Вставка и удаление элемента за O(log(n)).
     */
    private static void testPriorityQueue() {
        List<Integer> digits = Arrays.asList(3, 5, 1, 7, 2, 3, 5);
        leastN(digits, 4);
        System.out.println(digits); // [3, 5, 1, 7, 2, 3, 5]

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        priorityQueue.addAll(Arrays.asList(3, 5, 1, 7, 2, 3, 5));
        System.out.println(priorityQueue); // [1, 2, 3, 7, 5, 3, 5]

        priorityQueue.peek();
        priorityQueue.poll();
    }

    /**
     * ArrayDeque
     * В основе массив, но не позволяет обращаться к элементам по индексу и хранение null.
     *
     * Работает быстрее чем Stack, если используется как LIFO коллекция, а также быстрее чем
     * LinkedList, если используется как FIFO.
     */
    private static void testArrayDeque() {
        ArrayDeque arrayDeque = new ArrayDeque(Arrays.asList(4, 6, 1, 0, 5));
        arrayDeque.peek(); //peekLast(), peekFirst()
        arrayDeque.add(4); // addFirst(), addLast()
        arrayDeque.poll(); // pollFirst(), pollLast()
        arrayDeque.offer(5); // offerFirst(), offerLast()
        arrayDeque.pop();
        arrayDeque.push(4);
        arrayDeque.descendingIterator();
    }


    /**
     * PriorityQueue — это, на мой взгляд, самый недооценённый класс.
     * Многие сталкиваются с задачей отыскать, скажем, 10 минимальных значений большого несортированного
     * списка. Чаще всего список сортируют и потом берут первые 10 значений. Если исходный список менять
     * нельзя, придётся его ещё скопировать для сортировки. А ведь очередь с приоритетом легко справится с
     * этой задачей.
     * Такой код в зависимости от данных может работать гораздо быстрее, чем сортировка.
     * Например, для n = 10 и случайно заполненного списка из миллиона элементов очередь с приоритетом
     * почти в сто раз обгоняет подход с сортировкой. При этом дополнительной памяти требуется O(n) и
     * входные элементы можно обрабатывать в потоковом режиме (например, выбрать 10 наименьших чисел из
     * входного файла).
     *
     * @param input
     * @param n
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> List<T> leastN(Collection<T> input, int n) {
        assert n > 0;
        PriorityQueue<T> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (T t : input) {
            if (pq.size() < n) {
                pq.add(t);
            } else if (pq.peek().compareTo(t) > 0) {
                pq.poll();
                pq.add(t);
            }
        }
        List<T> list = new ArrayList<>(pq);
        Collections.sort(list);
        return list;
    }
}
