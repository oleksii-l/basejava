package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume get(String uuid) {
        int index = getIndexOf(uuid);

        if (index < 0) {
            System.out.println("ERROR: Resume is NOT present");
            return null;
        }

        return storage[index];
    }

    public int size() {
        return size;
    }


    public void save(Resume r) {
        int index = getIndexOf(r.getUuid());

        if (index >= 0) {
            System.out.println("ERROR: Resume is already present");
            return;
        }

        if (size >= STORAGE_LIMIT) {
            System.out.println("ERROR: storage is FULL");
            return;
        }

        __save(index, r);
        size++;
    }

    protected abstract void __save(int index, Resume r);


    public void update(Resume r) {
        int index = getIndexOf(r.getUuid());

        if (index < 0) {
            System.out.println("ERROR: Resume is NOT present");
            return;
        }

        __update(index, r);

    }

    protected abstract void __update(int index, Resume r);


    public void delete(String uuid) {
        int index = getIndexOf(uuid);

        if (index < 0) {
            System.out.println("ERROR: Resume is NOT present");
            return;
        }

        __delete(index);
        size--;

    }

    protected abstract void __delete(int index);


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {

        return Arrays.copyOfRange(storage, 0, size);
    }

    protected int getIndexOf(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

}
