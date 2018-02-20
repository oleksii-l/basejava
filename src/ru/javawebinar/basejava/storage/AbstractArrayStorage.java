package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected int size = 0;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndexOf(uuid);

        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }

        return storage[index];
    }

    @Override
    protected void __save(int index, Resume r){
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }

        saveIntoArray(index, r);
        size++;
    }

    protected abstract void saveIntoArray(int index, Resume r);

    @Override
    public void __update(int index, Resume r) {
        storage[index] = r;
    }

    @Override
    protected void __delete(int index) {
        deleteArray(index);

        storage[size - 1] = null;
        size--;
    }

    protected abstract void deleteArray(int index);

    protected abstract int getIndexOf(String uuid);


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {

        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }

    public int capacity() {
        return STORAGE_LIMIT;
    }
}
