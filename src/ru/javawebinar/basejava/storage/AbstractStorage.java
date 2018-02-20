package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public abstract void clear();

    @Override
    public void update(Resume r){
        int index = getIndexOf(r.getUuid());

        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        }

        __update(index, r);
    }

    protected abstract void __update(int index, Resume r);

    @Override
    public void save(Resume r) {
        int index = getIndexOf(r.getUuid());

        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        }

        __save(index, r);

    }

    protected abstract int getIndexOf(String uuid);

    protected abstract void __save(int index, Resume r);

    @Override
    public abstract Resume get(String uuid);

    @Override
    public void delete(String uuid) {
        int index = getIndexOf(uuid);

        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }

        __delete(index);
    }

    protected abstract void __delete(int index);

    @Override
    public abstract Resume[] getAll();

}
