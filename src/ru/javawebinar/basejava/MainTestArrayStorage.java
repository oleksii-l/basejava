package ru.javawebinar.basejava;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SortedArrayStorage;
import ru.javawebinar.basejava.storage.Storage;

/**
 * Test for ru.javawebinar.basejava.storage.ArrayStorage
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "Ivanov");
        Resume r2 = new Resume("uuid2", "Petrov");
        Resume r3 = new Resume("uuid3", "Putin");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        try {
            ARRAY_STORAGE.get("dummy");
        } catch (NotExistStorageException e) {
            System.out.println("Get dummy: OK");
        }

        printAll();

        Resume r2_forUpdate = new Resume("uuid2", null);
        ARRAY_STORAGE.update(r2_forUpdate);
        Resume r2_AfterUpdate = ARRAY_STORAGE.get("uuid2");
        if (r2 != r2_AfterUpdate) {
            System.out.println("Update of r2 was successful");
        } else {
            System.out.println("ERROR: r2 was NOT updated");
        }

        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
