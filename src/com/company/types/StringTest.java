package com.company.types;

public class StringTest {

    public static void main(String[] args) {

    }


    /**
     * Никогда не используйте операции конкатенации (оператор +) строки в цикле,
     * особенно если таких операций у вас много, это может очень существенно снизить
     * производительность. Все это происходит потому, что в приведенном выше примере
     * «s = s + fileds[i]» выполняется целых 3 операции:
     * создается StringBuilder на основе строки s,
     * вызывается метод конкатенации append,
     * вызывается метод toString (выглядит так: s = new StringBuilder(s).append(fields[i]).toString();).
     * Целых 3 операции вместо одной! Помимо этого каждый результат s + fileds[i] будет
     * занимать память в куче, как отдельная строка.
     */
    private void testAppend() {
        //медленно
        String[] fields1 = new String[]{"a", "b", "c", "d", "e", "f", "g"};
        String s1 = "";
        for (int i = 0; i < fields1.length; i++) {
            s1 = s1 + fields1[i];
        }

        //быстро
        String[] fields2 = new String[]{"a", "b", "c", "d", "e", "f", "g"};
        StringBuilder s2 = new StringBuilder();
        for (int i = 0; i < fields2.length; i++) {
            s2.append(fields2[i]);
        }
    }

    /**
     * Всегда ипользуйте StringBuilder, кроме случаев, когда вам необходимо использовать
     * конкретно StringBuffer, так как в StringBuilder нету синхронизированных методов в
     * отличие от StringBuffer и следовательно производительность будет выше, хоть и не значительно.
     */
    private void testBuffer() {

    }

    /**
     * Сравнение по ссылке, определенно, на порядок быстрее стравнения строк или других
     * обьектов. Правда, в этом случае есть один большой минус — стоимость поддержки приложения
     * увеличивается, особенно это становится заметным, когда нужно добавить, удалить или изменить
     * одно из существующих состояний. Но в случае если это неизменные свойства, как месяц года,
     * то смело используйте перечисление.
     */
    private void testConverting() {
        //медленно
        int a1 = 12;
        String s1 = a1 + "";
        //быстро
        int a2 = 12;
        String s2 = String.valueOf(a2);
    }

    // не нужна проверка на null
    private void testEmptyString() {
        String name = "";
        //медленно
        if (name.equals(""));
        //быстро
        if (name.isEmpty());
    }


    private void testComparison() {
        String name = "";
        //плохо
        if (name.equals("broadcast"));
        //хорошо
        if ("broadcast".equals(name));
    }


    private void testEnum() {
        Plan plan = new Plan();
        //медленно
        String status1 = plan.getStatus();
        if (status1.equals("draft")) {
            //do stuff
        } else if (status1.equals("submitted")) {
            //do stuff
        }
        //быстро
        PlanStatus status2 = plan.getStatusEnum();
        if (status2 == PlanStatus.DRAFT) {
            //do stuff
        } else if (status2 == PlanStatus.SUBMITTED) {
            //do stuff
        }
    }

    class Plan {
        String status;

        public String getStatus() {
            return status;
        }

        public PlanStatus getStatusEnum() {
            return PlanStatus.DRAFT;
        }

        public void setStatus(String status) {
            this.status = status;
        }


    }

    enum PlanStatus {
        DRAFT, SUBMITTED;
    }


}
