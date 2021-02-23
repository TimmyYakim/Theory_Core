package com.company.collections;

import java.util.*;

/**
 * Map предоставляет базовые методы для работы с данными вида «ключ — значение».
 *
 * Choosing the Right Map
 *
 * Having looked at HashMap and LinkedHashMap implementations previously and now TreeMap,
 * it is important to make a brief comparison between the three to guide us on which one fits where.
 *
 * A hash map is good as a general-purpose map implementation that provides rapid storage and retrieval
 * operations. However, it falls short because of its chaotic and unorderly arrangement of entries.
 * This causes it to perform poorly in scenarios where there is a lot of iteration as the entire capacity
 * of the underlying array affects traversal other than just the number of entries.
 *
 * A linked hash map possesses the good attributes of hash maps and adds order to the entries.
 * It performs better where there is a lot of iteration because only the number of entries is taken
 * into account regardless of capacity.
 *
 * A tree map takes ordering to the next level by providing complete control over how the keys should
 * be sorted. On the flip side, it offers worse general performance than the other two alternatives.
 * We could say a linked hash map reduces the chaos in the ordering of a hash map without incurring
 * the performance penalty of a tree map.
 *
 */
public class MapTest {

    public static void main(String[] args) {
        new MapTest().testHashTable();
        new MapTest().testHashMap();
    }


    /**
     * HashTable
     * В основе массив. Каждая ячейка которого - бакет.
     * Каждый бакет может содержать null либо одну и более пару "ключ-значение".
     * Индекс каждой пары вычисляется.
     * Нужна функция для приведения ключей к индексам ячеек. Поиск по индексу ячеек бустрее прямого перебора
     * всех ключей.
     * Ключи уникальны. В качестве ключей используются объекты чем числа, так как при большом диапазоне
     * чисел и малом количестве пар будет нерационально использоваться память.
     * Хэш-код вычисляется на основе адреса объекта в памяти и возвращается в виде int.
     *
     * При put(), get(), remove() вычисляется индекс ячейки на основе хэш-кода.
     * Даже у различных хэш-кодов может совпасть индекс ячейки (коллизия).
     * Коллизия возникает потому что разные ключи могут иметь одинаковый хэшкод. Однако одинаковые ключи
     * должны иметь одинаковый хэшкод.
     * Коллизии решаются тем, что добавляя элементы в один бакет они связываются в виде LinkedList.
     * Коллизии снижают производительность этой структуры данных (приходится обходить связные списки
     * в одном бакете).
     * load factor позволяет соблюсти баланс между размером массива и производительностью (коллизиями).
     * load factor = 0.75 - при заполненности 3/4 бакетов массив увеличивается в размере.
     * При превышении количества элементов 8 в одном бакете, связный список преобразуется в бинарное дерево.
     *
     * 1. key.hashCode()
     * 2. indexFor(hash, tableLength)
     * 3. получаем список (цепочку) элементов, привязанных к этой ячейке
     *
     * У HashMap есть такая же «проблема» как и у ArrayList — при удалении элементов размер массива table[]
     * не уменьшается. И если в ArrayList предусмотрен метод trimToSize(), то в HashMap таких методов нет
     *
     * Не позволяет использовать null в качестве значения или ключа.
     * Не является упорядоченной: порядок хранения элементов зависит от хэш-функции.
     * Добавление элемента за O(1), в худшем случае за O(n) или O(log(n)) при коллизиях
     * Поиск элемента за O(1), в худшем случае за O(n) при коллизиях, O(log(n)) при превышении в одном бакете 8:
     * - containsKey("E-Bike") за O(1)
     * - containsValue(eBike) за O(n)
     * Удаление элемента в идеале за O(1), но может быть за O(n) или O(log(n)) (зависит от распределения хэш-функции)
     * Синхронизирована.
     *
     * Не рекомендуется использовать из-за проблем с производительностью.
     * Вместо нее используют  ConcurrentHashMap.
     */
    private void testHashTable() {
        Hashtable table = new Hashtable();
        Word word = new Word("cat");
        table.put(word, "an animal");
        table.put(new Word("dog"), "another animal");
        // Для извлечения одного и того же объекта при равных ключах нужно переписать equals()
        String extracted = (String) table.get(new Word("cat"));

        // Итерирование не соблюдает порядок добавления элементов в коллекцию
//        Iterator<Word> it = table.keySet().iterator();
//        table.remove(new Word("dog"));
//        while (it.hasNext()) {
//            Word key = it.next(); // ConcurrentModificationException
//        }
        Enumeration<Word> enumKey = table.keys();
        table.remove(new Word("dog"));
        while (enumKey.hasMoreElements()) {
            Word key = enumKey.nextElement(); // нет исключения
        }

        String definition = (String) table.getOrDefault(new Word("dog"), "not found");
        table.putIfAbsent(new Word("dog"), definition);

        // Удалить объект с ключом new Word("cat") и значением "an animal"
        boolean result = table.remove(new Word("cat"), "an animal");
        table.replace(new Word("cat"), "a small domesticated carnivorous mammal", definition);
        table.computeIfAbsent(new Word("cat"), key -> "an animal");
        table.computeIfPresent(word, (key, value) -> ((Word)key).getName() + " - " + value);

        table.values();
        table.keys();
        table.keySet();
        table.containsKey(new Word("cat"));
        table.containsValue("an animal");
        table.isEmpty();
    }

    /**
     * HashMap
     * Полностью аналогична Hashtable, но  HashMap не синхронизирована и HashMap позволяет использовать null
     * как в качестве ключа, так и значения.
     *
     * Не является упорядоченной: порядок хранения элементов зависит от хэш-функции.
     * Добавление элемента за O(1), в худшем случае за O(n) или O(log(n)) при коллизиях
     * Поиск элемента за O(1), в худшем случае за O(n) при коллизиях, O(log(n)) при превышении в одном бакете 8:
     * - containsKey("E-Bike") за O(1)
     * - containsValue(eBike) за O(n)
     * Удаление элемента в идеале за O(1), но может быть за O(n) или O(log(n)) (зависит от распределения хэш-функции)
     * Не синхронизирована.
     *
     */
    private void testHashMap() {
        // методы как в Hashtable

        Collections.synchronizedMap(new HashMap<>());

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        map.put("key2", "value2");

//        Iterator<String> iterator = map.keySet().iterator();
//        while(iterator.hasNext()){
//            iterator.next(); // ConcurrentModificationException
//            map.put("key4", "value4");
//        }

        // Итерировние
        map.keySet();
        map.entrySet();
        map.values();


        Map<String, String> mutableMap = new HashMap<>();
        mutableMap.put("USA", "North America");
        Map<String, String> unmodifiableMap = Collections.unmodifiableMap(mutableMap);
//        unmodifiableMap.remove("USA"); // нельзя
        mutableMap.remove("USA"); // а так можно, тк обертка
        unmodifiableMap.containsKey("USA"); // false



    }

    /**
     * LinkedHashMap
     * это упорядоченная реализация хэш-таблицы.
     * Наследуется от HashMap. Реализация как в HashMap, но элементы дополнительно связаны
     * двунаправленными связями before and after .
     * Порядок итерирования равен порядку добавления элементов.
     * Данная особенность достигается благодаря двунаправленным связям между
     * элементами (аналогично LinkedList).
     *
     * Добавление элемента за O(log(n))
     * Поиск элемента за O(log(n))
     * Удаление элемента в идеале за O(log(n))
     * Не синхронизирована.
     */
    private static void testLinkedHashMap() {
        // аналогично HashMap
    }

    /**
     * TreeMap
     * реализация Map основанная на красно-чёрных деревьях.
     * a red-black tree is a self-balancing binary search tree
     * Как и LinkedHashMap является упорядоченной.
     * По-умолчанию, коллекция сортируется по ключам с использованием принципа "natural ordering", но это
     * поведение может быть настроено под конкретную задачу при помощи объекта Comparator, который указывается
     * в качестве параметра при создании объекта TreeMap.
     *TreeMap, unlike a hash map and linked hash map, does not employ the hashing principle anywhere since it
     * does not use an array to store its entries.
     *
     * Добавление элемента за O(1), в худшем случае за O(n) при коллизиях
     * Поиск элемента за O(1), в худшем случае за O(n) при коллизиях
     * Удаление элемента в идеале за O(1), но может быть за O(n) (зависит от распределения хэш-функции)
     * Не синхронизирована.
     */
    private static void testTreeMap() {
        TreeMap<String, String> tm = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        new TreeMap<>(Comparator.reverseOrder());
        tm.headMap(""); tm.tailMap("");
        tm.ceilingEntry(""); tm.floorEntry("");
        tm.ceilingKey(""); tm.floorKey("");
        tm.descendingKeySet();
        tm.descendingMap();
        tm.firstEntry(); tm.lastEntry();
        tm.firstKey(); tm.lastKey();
        tm.pollFirstEntry(); tm.pollLastEntry();
        tm.navigableKeySet();
    }

    /**
     * Основана на Hashtable
     * реализация хэш-таблицы, которая организована с использованием weak references. Другими словами,
     * Garbage Collector автоматически удалит элемент из коллекции при следующей сборке мусора, если на ключ
     * этого элеметна нет жёстких ссылок.
     *
     * Удобно использовать при кэшировании объетов большого объема, например изображений
     */
    private static void testWeakHashMap() {

    }


    /**
     * При добавлении в хэш-таблицы обязательно соблюдение хэшкод-контракта:
     * одинаковые объекты должны возвращать одинаковый хэш-код.
     *
     * Note that hashCode() and equals() need to be overridden only for classes
     * that we want to use as map keys, not for classes that are only used as values in a map
     *
     * The general contract of hashCode() states:
     * 1. Whenever it is invoked on the same object more than once during an execution of a Java
     * application, hashCode() must consistently return the same value, provided no information used
     * in equals comparisons on the object is modified. This value needs not remain consistent from
     * one execution of an application to another execution of the same application
     * 2. If two objects are equal according to the equals(Object) method, then calling the hashCode()
     * method on each of the two objects must produce the same value
     * 3. It is not required that if two objects are unequal according to the equals(java.lang.Object)
     * method, then calling the hashCode method on each of the two objects must produce distinct integer
     * results. However, developers should be aware that producing distinct integer results for unequal
     * objects improves the performance of hash tables
     */
    private class Word {
        private String name;

        public Word(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        // По умолчанию если метод не переопределить, то будут сравниваться ссылки объектов, т.е. для
        // двух объектов с одинаковыми полями будет возвращен false
        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof Word))
                return false;
            Word word = (Word) o;
            return word.getName().equals(this.name);
        }

        // Если не переопределить этот метод, то объекты с одинаковыми ключами могут попасть в разные бакеты
        // (разные хэш-коды)
        // Также рекомендуется добиться того, чтобы разный хэшкод был для разных объектов,
        // чтобы не было коллизий.
        // В классах String, Integer, Long или другом классе-обертке equal() и hashCode() уже переопределены
        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }

}
