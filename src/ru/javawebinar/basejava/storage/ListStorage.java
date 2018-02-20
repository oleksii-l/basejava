package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();


    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume get(String uuid) {

        List<Resume> res = storage.stream()
                .filter(r -> r.getUuid().equals(uuid))
                .collect(Collectors.toList());

        if (res.isEmpty()) {
            throw new NotExistStorageException(uuid);
        }

        return res.get(0);
    }

    @Override
    protected void __save(int index, Resume r) {
        storage.add(r);
    }

    @Override
    public void __update(int index, Resume r) {
        storage.set(index, r);
    }

    @Override
    public void __delete(int index) {
        storage.remove(index);
    }

    @Override
    protected int getIndexOf(String uuid) {
        int i = 0;
        for (Resume r: storage) {
            if (r.getUuid().equals(uuid)) {
                return i;
            }
            i++;
        }

        return -1;

    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        Resume[] res = new Resume[size()];

        return storage.toArray(res);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
