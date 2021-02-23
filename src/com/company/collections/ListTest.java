package com.company.collections;

import sun.plugin.javascript.navig4.Link;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

/**
 * Упорядоченные коллекции. Возможность доступа к элементам по индексу
 * и по значению (первое найденное вхождение).
 */
public class ListTest {

    public static void main(String[] args) {
        testArrayList();
        System.out.println("======================");
        testLinkedList();
    }


    /**
     * Динамически расширяемый массив. В основе обычный массив.
     * обычный массив => "Произвольный доступ" (Random Access) к элементам (по индексу без перебора).
     * При расширении новый размер: (oldCapacity * 3) / 2 + 1 .
     * Массив копируется при помощи нативного System.arraycopy() (большая скорость!!!).
     * Производительность:
     * — Доступ к по индексу за O(1);
     * — Поиск по значению за O(n) в неотсортированном и O(log n) в отсортированном (бинарный поиск);
     * — Вставка и удаление из «середины» списка O(n);
     * — Позволяет хранить любые значения в том числе и null;
     * — Не синхронизирован.
     */
    private static void testArrayList() {
        List<Integer> digits = Arrays.asList(5, 8, 2, 11, 7); // не поддерживает изменения размера
        List<Integer> someDigits = new ArrayList<>();
        someDigits.add(6);
        someDigits.add(7);
//        digits.addAll(someDigits); // java.lang.UnsupportedOperationException
        List<Integer> allDigits = new ArrayList<>(digits);
        // Лучше так чем добавлять по одиночке, производительность выше
        allDigits.add(4, 4); // [5, 8, 2, 11, 4, 7]
        allDigits.addAll(someDigits); // [5, 8, 2, 11, 4, 7, 6, 7]
        Collections.addAll(allDigits, 12, 1); // [5, 8, 2, 11, 4, 7, 6, 7, 12, 1]

        allDigits.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        // [1, 2, 4, 5, 6, 7, 7, 8, 11, 12]

//        при удалении элементов текущая величина capacity не уменьшается => trimToSize()
        // удаление по индексу
        allDigits.remove(allDigits.lastIndexOf(7)); // [1, 2, 4, 5, 6, 7, 8, 11, 12]
        // удаление по значению
        allDigits.remove(Integer.valueOf(5)); // [1, 2, 4, 6, 7, 8, 11, 12]
        allDigits.removeAll(Arrays.asList(4, 5)); // [1, 2, 6, 7, 8, 11, 12]
        allDigits.removeIf(i -> i % 2 == 0); // [1, 7, 11]


        allDigits.contains(7); // true
        allDigits.containsAll(Arrays.asList(6, 7, 8)); // false

//        allDigits.get(5); // IndexOutOfBoundsException
        allDigits.get(2); // 11
        allDigits.set(2, 3); // [1, 7, 3]

        allDigits.replaceAll(new UnaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return (integer % 2 != 0) ? (integer + 1) : integer;
            }
        });
        // [2, 8, 4]

        List<Integer> threeDigits = Arrays.asList(5, 2, 7);
        allDigits.addAll(threeDigits); // [2, 8, 4, 5, 2, 7]
        allDigits.retainAll(threeDigits); // [2, 5, 2, 7]

        allDigits.clear();
        allDigits.isEmpty(); // true

        List<Integer> fiveDigits = Arrays.asList(5, 2, 7, 13, 0);
        ListIterator<Integer> it = fiveDigits.listIterator(fiveDigits.size());
        List<Integer> result = new ArrayList<>(fiveDigits.size());
        while (it.hasPrevious())
            result.add(it.previous());
        System.out.println(result); // [0, 13, 7, 2, 5]

    }


    /**
     * LinkedList — класс, реализующий два интерфейса — List и Deque.
     * В основе не массив, а связный двунаправленный список из Entry (value, previous, next).
     * связный список => "Последовательный доступ" (Sequential Access)
     * Итератор поддерживает обход в обе стороны.
     *
     * Производительность:
     * — Доступ к по индексу за O(n);
     * — Поиск по значению за O(n);
     * — Вставка и удаление из «середины» списка O(n);
     *   Используя ListIterator.add() и ListIterator.remove(), потребуется O(1)
     *   (если находимся на нужном элементе)
     * — Вставка и удаление в начало и в конец O(1) (removeFirst(), removeLast(), но remove() за O(n));
     * — Позволяет хранить любые значения в том числе и null;
     * — Не синхронизирован.
     */
    private static void testLinkedList() {
        LinkedList<Integer> numbers = new LinkedList<>();
        Collections.addAll(numbers, 3, 4, 11, 0, 5, 2, 5, 7);
        // методы как у ArrayList, но есть унаследованные от Deque
        List<Integer> result = new ArrayList<>();
        Iterator<Integer> it = numbers.descendingIterator();
        while (it.hasNext())
            result.add(it.next());
        System.out.println(result); // [7, 5, 2, 5, 0, 11, 4, 3]

//        addFirst(i), addLast(i);
//        offer(i), offerFirst(i), offerLast(i)
//        offer vs add: offer return false, add - IllegalStateException

//        numbers.getFirst();
//        numbers.getLast();
//        removeFirst(), removeLast();
//        removeFirstOccurrence(i), removeLastOccurrence(5);

//        retrieves, but does not remove
//        peek(), peekFirst(), peekLast();

//        retrieves and remove
//        poll(), pollFirst(), pollLast();

//        numbers.pop();
//        numbers.push();
    }


    /**
     * Динамический массив.
     * Аналогичен ArrayList, но потокобезопасен.
     *
     * Не рекомендуется использовать. В качестве альтернативы часто применяется аналог — ArrayList.
     * Если требуется синхронизация, то Collections.synchronizedList()
     */
    private static void testVector() {
    }


    /**
     * Расширение коллекции Vector. LIFO
     *
     * Не рекомендуется использовать. В качестве альтернативы часто применяется аналог — ArrayDeque.
     * Стэк реализует дополнительные методы: peek (взглянуть, посмотреть), pop (вытолкнуть), push (затолкать).
     */
    private static void testStack() {

    }


}
