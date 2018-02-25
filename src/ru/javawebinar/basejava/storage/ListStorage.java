package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();


    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume doGet(Object index) {
        return storage.get((Integer) index);
    }

    @Override
    protected void doSave(Object index, Resume r) {
        storage.add(r);
    }

    @Override
    public void doUpdate(Object index, Resume r) {
        storage.set((Integer) index, r);
    }

    @Override
    public void doDelete(Object index) {
        storage.remove(((Integer) index).intValue());
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    @Override
    public List<Resume> getAllSorted() {
        storage.sort(new ResumeComparator());
        return storage;
    }

}
