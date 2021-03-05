package com.company.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOTest {

    public static void main(String[] args) {

    }


    private void testFileInputStream() {
        final String file = "draft";
        try {
            //медленно
            InputStream is = new FileInputStream(file);
            int val;
            while (true) {
                if (!((val = is.read()) != -1)) break;
            }
            //быстро
            InputStream is1 = new BufferedInputStream(new FileInputStream(file));
            int val1;
            while ((val1 = is1.read()) != -1) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * мы не знаем размер файла который считываем.
     * В общем случае старайтесь не считывать всё в массив байтов, у вас попросту может не хватить памяти.
     */
    private void test() {
//        //плохо
//        byte[] fileBinaryData = readFile(filepath);
//        //получше
//        InputStream fileStream = readFile(filepath);
    }


}
