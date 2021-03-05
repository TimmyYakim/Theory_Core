package com.company.io;

import java.io.*;
import java.nio.charset.Charset;

public class InputStreamTest {


    private void testByteArrayInputStream() {
        byte[] array1 = new byte[]{1, 3, 5};
        ByteArrayInputStream byteStream1 = new ByteArrayInputStream(array1);
        int b;
        while ((b = byteStream1.read()) != -1)
            System.out.println(b);

        String str = "Hello";
        byte[] array2 = str.getBytes(Charset.defaultCharset());
        ByteArrayInputStream byteStream2 = new ByteArrayInputStream(array2, 0, 2);
        int c;
        while ((c=byteStream2.read()) != -1)
            System.out.println((char)c);
    }

    private void testFileInputStream() {
        try (FileInputStream fin = new FileInputStream("file.txt")) {
            fin.available(); // размер
            int i;
            while ((i=fin.read()) != -1)
                System.out.println((char)i);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        String text = "Hello";
        try (FileOutputStream fos = new FileOutputStream("file.txt")) {
            byte[] buffer = text.getBytes();
            fos.write(buffer, 0, 2);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void testBufferedInputStream() {
        String text = "Hello";
        try (FileOutputStream fos = new FileOutputStream("file.txt");
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            byte[] buffer = text.getBytes();
            bos.write(buffer, 0, buffer.length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    /**
     * не сериализуются поля static и transient
     */
    private void testObjectInputStream() {
        Person person = new Person(12, "Tom");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.dat"))) {
            oos.writeInt(person.getAge());
            oos.writeUTF(person.getName());
//            oos.writeObject(person);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    class Person implements Serializable {

        // для правильной сериализации и десирализации на каждую новую версию объекта новый UID
        private static final long serialVersionUID = 245464215;

        int age;
        String name;
        transient String type = "Person";

        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
