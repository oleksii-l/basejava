package ru.javawebinar.basejava;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * gkislin
 * 21.07.2016
 */
public class MainFile {
    private static int recursionLevel = 0;
    private static final String SHIFT = "..";

    public static void main(String[] args) {
        String filePath = ".gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./");
//        System.out.println(dir.isDirectory());
//        String[] list = dir.list();
//        if (list != null) {
//            for (String name : list) {
//                System.out.println(name);
//            }
//        }
//
//        try (FileInputStream fis = new FileInputStream(filePath)) {
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        printDirectoryDeeply(dir);
    }

    public static void printDirectoryDeeply(File dir) {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(getCurrentShift()
                            + "File: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(getCurrentShift() +
                            "Directory: " + file.getName());
                    recursionLevel++;
                    printDirectoryDeeply(file);
                    recursionLevel--;
                }
            }
        }
    }

    private static String getCurrentShift() {
        return Collections.nCopies(recursionLevel, SHIFT).stream().collect(Collectors.joining(""));
    }
}
