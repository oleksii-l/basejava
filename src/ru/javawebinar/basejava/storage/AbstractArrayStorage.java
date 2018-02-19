package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume get(String uuid) {
        int index = getIndexOf(uuid);

        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }

        return storage[index];
    }

    public int size() {
        return size;
    }

    protected abstract void __save(int index, Resume r);


    public void update(Resume r) {
        int index = getIndexOf(r.getUuid());

        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        }

        storage[index] = r;
    }


    public void delete(String uuid) {
        int index = getIndexOf(uuid);

        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }

        __delete(index);

        storage[size - 1] = null;
        size--;
    }

    protected abstract void __delete(int index);

    protected abstract int getIndexOf(String uuid);


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {

        return Arrays.copyOfRange(storage, 0, size);
    }

    public int capacity() {
        return storage.length;
    }

}
