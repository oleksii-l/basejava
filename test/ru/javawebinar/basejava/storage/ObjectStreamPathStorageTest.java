package ru.javawebinar.basejava.storage;

import org.junit.BeforeClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {


    public static final String DIR = "./out/teststorage";

    @BeforeClass
    public static void init() {
        Path testDir = Paths.get(DIR);
        if (!Files.exists(testDir)) {
            try {
                Files.createDirectory(testDir);
            } catch (IOException e) {
                throw new RuntimeException("Could not create output directory:" + DIR);
            }
        }
    }

    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(DIR));
    }
}
