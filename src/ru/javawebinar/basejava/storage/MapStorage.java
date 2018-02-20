package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapStorage extends AbstractStorage {
    @Override
    public void clear() {

    }

    @Override
    public void update(Resume r) {

    }

    @Override
    protected void __update(int index, Resume r) {

    }

    @Override
    public void save(Resume r) {

    }

    @Override
    protected int getIndexOf(String uuid) {
        return 0;
    }

    @Override
    protected void __save(int index, Resume r) {

    }

    @Override
    public Resume get(String uuid) {
        return null;
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    protected void __delete(int index) {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }

}
