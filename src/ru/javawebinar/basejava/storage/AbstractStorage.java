package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;

public abstract class AbstractStorage implements Storage {

    protected abstract Integer getSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected abstract void doUpdate(Object searchKey, Resume r);

    protected abstract void doSave(Object searchKey, Resume r);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doDelete(Object searchKey);


    @Override
    public abstract void clear();

    @Override
    public void update(Resume r) {
        Object searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(searchKey, r);
    }

    @Override
    public void save(Resume r) {
        Object searchKey = getNotExistingSearchKey(r);
        doSave(searchKey, r);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public abstract Resume[] getAll();


    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);

        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(Resume r) {
        Object searchKey = getSearchKey(r.getUuid());

        if (isExist(searchKey)) {
            throw new ExistStorageException(r.getUuid());
        }
        return searchKey;
    }

    static class ResumeComparator implements Comparator<Resume> {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getFullName().compareTo(o2.getFullName());
        }
    }

}
