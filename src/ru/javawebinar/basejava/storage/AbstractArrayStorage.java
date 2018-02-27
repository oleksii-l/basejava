package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;

    protected int size = 0;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected abstract void saveIntoArray(int index, Resume r);

    protected abstract void deleteArray(int index);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume doGet(Integer index) {
        return storage[index];
    }

    @Override
    protected void doSave(Integer index, Resume r) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }

        saveIntoArray(index, r);
        size++;
    }


    @Override
    public void doUpdate(Integer index, Resume r) {
        storage[index] = r;
    }

    @Override
    protected void doDelete(Integer index) {
        deleteArray(index);

        storage[size - 1] = null;
        size--;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.stream(storage)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public int size() {
        return size;
    }

    public int capacity() {
        return STORAGE_LIMIT;
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }
}
