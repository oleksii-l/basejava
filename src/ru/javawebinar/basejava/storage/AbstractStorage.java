package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected int size = 0;

    @Override
    public abstract void clear();

    @Override
    public abstract void update(Resume r);

    @Override
    public void save(Resume r) {
        int index = getIndexOf(r.getUuid());

        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        }

        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }

        __save(index, r);
        size++;
    }

    protected abstract int getIndexOf(String uuid);

    protected abstract void __save(int index, Resume r);

    @Override
    public abstract Resume get(String uuid);

    @Override
    public abstract void delete(String uuid);

    @Override
    public abstract Resume[] getAll();

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacity() {
        return STORAGE_LIMIT;
    }
}
