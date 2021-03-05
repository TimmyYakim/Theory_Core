package com.company.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.company.io.Line;
import com.company.io.Points;


/**
 * Сериализация позволяет расширить эти рамки и «дать жизнь» объекту так же между запусками программы.
 * Дополнительным бонусом ко всему является сохранение кроссплатформенности.
 * Не важно какая у вас операционная система, сериализация переводит объект в поток байтов, который
 * может быть восстановлен на любой ОС. Если вам необходимо передать объект по сети, вы можете сериализовать
 * объект, сохранить его в файл и передать по сети получателю.
 *  Так же сериализация позволяет осуществлять удаленный вызов методов (Java RMI)
 *
 *  В процессе сериализации вместе с сериализуемым объектом сохраняется его граф объектов.
 *  Т.е. все связанные с этим объекто, объекты других классов так же будут сериализованы вместе с ним.
 *
 */
public class ObjectOutputTest {

    public static void main(String[] args) throws IOException {
        new ObjectOutputTest().start();
    }

    private void start() {
        Line line1 = new Line(new Points(1,1), new Points(2, 2));
        Line line2 = new Line(new Points(2,2), new Points(3, 3));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("lines.dat"))) {
            oos.writeObject(line1);
            oos.writeObject(line2);
            oos.reset(); // чтобы дальнейшие изменения применились
            line1.setP2(new Points(5, 6));
            oos.writeObject(line1);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }


        List<Line> lines = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("lines.dat"))) {
            for (int i=0; i < 3; i++) {
                lines.add((Line) ois.readObject());
            }
            lines.forEach(System.out::println);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

//        Line{p1=Point{x=1, y=1}, p2=Point{x=2, y=2}}
//        Line{p1=Point{x=2, y=2}, p2=Point{x=3, y=3}}
//        Line{p1=Point{x=1, y=1}, p2=Point{x=5, y=6}}
    }



}
