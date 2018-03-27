package ru.javawebinar.basejava.storage;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.io.File;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FileStorageTest extends AbstractStorageTest {
    public static String STORAGE_DIR = "./out/teststorage";

    @BeforeClass
    public static void init() {
        File testStorageDir = new File(STORAGE_DIR);
        if (!testStorageDir.exists()) {
            testStorageDir.mkdir();
        } else {
            File[] files = testStorageDir.listFiles();
            if (files == null) {
                return;
            }
            for (File file : files) {
                file.delete();
            }
        }
    }

    public FileStorageTest() {
        super(new AbstractFileStorage(new File(STORAGE_DIR)) {
        });
    }
}
