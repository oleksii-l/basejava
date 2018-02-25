package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveIntoArray(int index, Resume r) {
        int newIndex = -index - 1;
        System.arraycopy(storage, newIndex, storage, newIndex + 1, size - newIndex);
        storage[newIndex] = r;
    }

    @Override
    protected void deleteArray(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }


    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, null);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

}
