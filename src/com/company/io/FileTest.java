package com.company.io;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileTest {

    private static final String FILE_NAME = "fileToCreate.txt";
    private static final String TARGET_FILE = "targetFileToMove.txt";

    public static void main(String[] args) throws IOException {
        testCreateFile();
    }

    private static void testCreateFile() throws IOException {
        FileSystems.getDefault().getRootDirectories(); // C:\ D:\
//        //nio
//        Path newFilePath = Paths.get(FILE_NAME);
//        Files.createFile(newFilePath);
//        //io.File
//        File newFile = new File(FILE_NAME);
//        boolean success = newFile.createNewFile();
        //io.FileOutputStream
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME)) {
        }
    }

    private static void testWriteFile() throws IOException {
        String str = "Hello";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(str);
        }

        // we can use PrintWriter to write formatted text to a file
        try (FileWriter fileWriter = new FileWriter(FILE_NAME);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.print("Some String");
            printWriter.printf("Product name is %s and its price is %d $", "iPhone", 1000);
        }

        //FileOutputStream to write binary data to a file
        try (FileOutputStream outputStream = new FileOutputStream(FILE_NAME)) {
            byte[] strToBytes = str.getBytes();
            outputStream.write(strToBytes);
        }

        // DataOutputStream
        try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
             DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos))) {
            outStream.writeUTF(str);
        }

        // RandomAccessFile enables us to write at a specific position in the file given the offset
        // — from the beginning of the file — in bytes

        try (RandomAccessFile writer = new RandomAccessFile(FILE_NAME, "rw")) {
            writer.seek(2L);
            writer.writeInt(2);
            long len = writer.length();
//            for (int i=0; i<writer.length(); i++) { // каждый раз вычисляет размер. съедает время
            for (int i=0; i<len; i++) { // верный
                System.out.println();
            }
        }
    }

    private static void testReadFile() throws IOException {
        // readFromInputStream
        StringBuilder resultStringBuilder = new StringBuilder();
        try (FileInputStream inputStream = new FileInputStream(FILE_NAME);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }

        // BufferedReader
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String currentLine = reader.readLine();
        }

        //JDK7, the NIO package
        Path path = Paths.get(FILE_NAME);
        // small file
        List<String> list = Files.readAllLines(path);
        // large file
        BufferedReader reader = Files.newBufferedReader(path);
        String line = reader.readLine();
        // JDK8 offers the lines()
        try (Stream<String> lines = Files.lines(path);) {
            String data = lines.collect(Collectors.joining("\n"));
        }

        // DataInputStream
        String result = null;
        try (DataInputStream reader2 = new DataInputStream(new FileInputStream(FILE_NAME))) {
            int nBytesToRead = reader2.available();
            if(nBytesToRead > 0) {
                byte[] bytes = new byte[nBytesToRead];
                reader2.read(bytes);
                result = new String(bytes);
            }
        }




    }

    private static void testMoveFile() throws IOException {
        // from the Java NIO package
        Path fileToMovePath = Paths.get(FILE_NAME);
        Path targetPath = Paths.get(TARGET_FILE);
        Files.move(fileToMovePath, targetPath);

        File fileToMove = new File(FILE_NAME);
        boolean isMoved = fileToMove.renameTo(new File(TARGET_FILE));
        if (!isMoved) {
            throw new FileSystemException(TARGET_FILE);
        }
    }


    private static void testDeleteFile() throws IOException {
        // Java 6 solution
        File fileToDelete = new File(FILE_NAME);
        boolean success = fileToDelete.delete();

        // Java 7 solution
        Path fileToDeletePath = Paths.get(FILE_NAME);
        Files.delete(fileToDeletePath);
        Files.deleteIfExists(fileToDeletePath);
    }

    private static void testFileExists() throws IOException {
        // java.nio.file.Files
        Path path = Paths.get(FILE_NAME);
        Files.exists(path);
        Path tempDirectory = Files.createTempDirectory("baeldung-exists");
        Files.exists(tempDirectory);
        Files.isDirectory(tempDirectory);

        // java.io.File
        File file = new File("invalid");
        file.exists();
        file.isFile();
    }

}
