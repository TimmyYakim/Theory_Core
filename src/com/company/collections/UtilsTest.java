package com.company.collections;

import java.util.*;

//Arrays
//Collections

/**
 * Всегда старайтесь в методах вашей бизнес логики возвращать пустые коллекции
 * вместо null значений, это избавляет от лишних null-проверок и делает код чище.
 * Для этого в классе Collections есть несколько замечательных методов:
 * Collections.emptyList();
 * Collections.emptyMap();
 * Collections.emptySet();
 */
public class UtilsTest {

    public static void main(String[] args) {
        testCollections();
        System.out.println("=================");
        new UtilsTest().testArrays();
    }

    private static void testSorting() {

        // Sorting Complete Array
        Arrays.sort(new int[] {1, 5, 2, 0});
        // Sorting a List
        Collections.sort(Arrays.asList(1, 5, 2, 0));
        // Sorting a Set
        Set<Integer> descSortedIntegersSet = new LinkedHashSet<>(
                Arrays.asList(new Integer[]
                        {255, 200, 123, 89, 88, 66, 7, 5, 1}));
        List<Integer> list = new ArrayList<>(descSortedIntegersSet);
        Collections.sort(list, Comparator.naturalOrder());


    }

    private static void testCollections() {
        List<Integer> collection = new ArrayList<>();
        Collections.addAll(collection, 11, 12, 13, 14);
        List<Integer> emptyList = Collections.EMPTY_LIST; // []


//        System.out.println(Collections.);

    }

    private void testArrays() {
        Integer[] someArray = new Integer[]{1, 2, 3};
        System.out.println(someArray); // [Ljava.lang.Integer;@1b6d3586
        System.out.println(Arrays.toString(someArray)); // [1, 2, 3]

        int[][] a = {
                {1, 2, 3},
                {4, 5, 6}
        };
        System.out.println(Arrays.deepToString(a)); // [[1, 2, 3], [4, 5, 6]]


        Element[] firstElementArray = { new Element("a"), new Element("b"), new Element("c") };
        Element[] secondElementArray = {new Element("c"), new Element("b"), new Element("a") };
        Element[] thirdElementArray = { new Element("a"), new Element("b"), new Element("c") };
        Arrays.equals(firstElementArray, secondElementArray); // false
        Arrays.equals(firstElementArray, thirdElementArray); // true


        Element[] copyOfElements = new Element[firstElementArray.length];
        // быстрое копирование элементов массива
        System.arraycopy(firstElementArray, 0, copyOfElements, 0, firstElementArray.length);


        String[] letters = new String[]{"b", "a", "c", "A", "D"};
        Arrays.sort(letters); // [A, D, a, b, c]
        Arrays.sort(letters, Collections.reverseOrder()); // [c, b, a, D, A]
        // Без учета регистра
        Arrays.sort(letters, String.CASE_INSENSITIVE_ORDER); // [a, A, b, c, D]
        System.out.println(Arrays.toString(letters));

        Collections.sort(Arrays.asList("b", "a", "c", "A", "D")); // сортирует только List
        Collections.reverse(Arrays.asList("b", "a", "c", "A", "D")); // сортирует только List

//        Collections.max(Collection);
//        Collections.max(Collection, Comparator);
//        Collections.min(Collection);
//        Collections.min(Collection, Comparator);


//        If you have a sorted array, then you may use a binary search algorithm which works faster than linear search:
//        List<String> copy = new ArrayList<>(stringsToSearch);
//        Collections.sort(copy);
//        int index = Collections.binarySearch(copy, "f");

        // только со списками
        List<String> students = Arrays.asList("Foo", "Bar", "Baz", "Qux");
        Collections.shuffle(students);
        int seedValue = 10;
        Collections.shuffle(students, new Random(seedValue));




    }





    private class Element {
        final String name;
        private Element(String name) { this.name = name; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Element element = (Element) o;
            return Objects.equals(name, element.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

}
