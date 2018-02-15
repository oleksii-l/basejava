package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {


    @Override
    protected void __save(int index, Resume r) {
        storage[size] = r;
    }


    @Override
    protected void __update(int index, Resume r) {
        storage[index] = r;
    }


    @Override
    protected void __delete(int index) {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
    }

}
