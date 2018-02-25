package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractArrayStorage extends AbstractStorage {
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
    public Resume doGet(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected void doSave(Object index, Resume r) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }

        saveIntoArray((Integer) index, r);
        size++;
    }


    @Override
    public void doUpdate(Object index, Resume r) {
        storage[(Integer) index] = r;
    }

    @Override
    protected void doDelete(Object index) {
        deleteArray((Integer) index);

        storage[size - 1] = null;
        size--;
    }

    @Override
    public List<Resume> getAllSorted() {
        return Arrays.stream(storage)
                .limit(size)
                .sorted(new ResumeComparator())
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
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }
}
