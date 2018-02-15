package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {


    @Override
    protected void __update(int index, Resume r) {
        __delete(index);
        size--;
        save(r);
    }

    @Override
    protected void __save(int index, Resume r) {
        int newIndex = -index - 1;
        Resume[] destination = new Resume[STORAGE_LIMIT];
        System.arraycopy(storage, 0, destination, 0, newIndex);
        destination[newIndex] = r;
        System.arraycopy(storage, newIndex, destination, newIndex + 1, size - newIndex);

        storage = destination;
    }

    @Override
    protected void __delete(int index) {
        Resume[] destination = new Resume[STORAGE_LIMIT];
        System.arraycopy(storage, 0, destination, 0, index);
        System.arraycopy(storage, index + 1, destination, index, size - index);
        storage = destination;
    }


    @Override
    protected int getIndexOf(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
