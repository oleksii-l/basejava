package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * gkislin
 * 21.07.2016
 */
public class MainFile {
    public static void main(String[] args) {

        String dirPath = "./";
        File projectDir = new File(dirPath);

        printDir(dirPath, projectDir);
    }

    public static void printDir(String path, File dir) {
        Objects.requireNonNull(dir);
        Arrays.stream(dir.list())
                .forEach(d -> {
                    File current = new File(path + '/' + d);
                    if (current.isDirectory()) {
                        printDir(current.getAbsolutePath(), current);
                    } else {
                        System.out.println(current.getName() + "|" + current.getAbsolutePath());
                    }
                });
    }
}
