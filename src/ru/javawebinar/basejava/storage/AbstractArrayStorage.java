package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public Resume get(String uuid) {
        int index = getIndexOf(uuid);

        if (index == -1) {
            System.out.println("ERROR: Resume is NOT present");
            return null;
        }

        return storage[index];
    }

    public int size() {
        return size;
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
