package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {


    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {

        if (getIndexOf(r.getUuid()) != -1) {
            System.out.println("ERROR: Resume is already present");
            return;
        }

        if (size >= STORAGE_LIMIT) {
            System.out.println("ERROR: storage is FULL");
        }

        storage[size] = r;
        size++;
    }


    public void update(Resume r) {
        int index = getIndexOf(r.getUuid());

        if (index == -1) {
            System.out.println("ERROR: Resume is NOT present");
            return;
        }

        storage[index] = r;

    }


    public void delete(String uuid) {
        int index = getIndexOf(uuid);

        if (index == -1) {
            System.out.println("ERROR: Resume is NOT present");
            return;
        }

        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {

        return Arrays.copyOfRange(storage, 0, size);
    }


}
