package Lesson1;

public class Main {
    public static void main(String[] args) {
        Account acc1=new Account("Новый");
        acc1.setName("Первый");
        acc1.addCurrenc(Currency.CNY,100);
        acc1.addCurrenc(Currency.USD,200);
        Save acc_s1=acc1.save();
        System.out.println("1. Проверка сохранения/отката    - "+acc1);
        System.out.println("Сохраняем     - "+acc1);
        acc1.setName("Первый1");
        acc1.setName("Первый2");
        acc1.setName("Первый3");
        acc1.addCurrenc(Currency.KAZ,1);
        acc1.addCurrenc(Currency.CNY,1000);
        System.out.println("Изменяем      - "+acc1);
        acc_s1.load();
        System.out.println("Откатываем    - "+acc1);
        System.out.println("2. Проверка изменения/отмнны    - "+acc1);
        acc1.setName("Первый2");
        System.out.println("Изменяем     1- "+acc1);
        acc1.setName("Первый3");
        System.out.println("Изменяем     2- "+acc1);
        acc1.addCurrenc(Currency.KAZ,1);
        System.out.println("Изменяем     3- "+acc1);
        acc1.addCurrenc(Currency.CNY,1000);
        System.out.println("Изменяем     4- "+acc1);

        acc1.undo();
        System.out.println("Отмена до шаг 3- "+acc1);
        acc1.undo();
        System.out.println("Отмена до шаг 2- "+acc1);
        acc1.undo();
        System.out.println("Отмена до шаг 1- "+acc1);


    }
}
