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
        size = 0;
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

    protected void __save(int index, Resume r) {
        storage.add(r);
    }

    @Override
    public void update(Resume r) {
        int index = getIndexOf(r.getUuid());

        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        }

        storage.set(index, r);
    }

    @Override
    public void delete(String uuid) {
        storage.remove(new Resume(uuid));
        size--;
    }

    protected int getIndexOf(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        Resume[] res = new Resume[size];

        return storage.toArray(res);
    }
}
