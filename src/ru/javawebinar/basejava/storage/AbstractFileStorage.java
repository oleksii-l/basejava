package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.util.*;

/**
 * gkislin
 * 22.07.2016
 */
public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Files list is absent.");
        }
        for (File file : files) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Files list is absent.");
        }
        return files.length;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("Writing to file failed");
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            doUpdate(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected void doWrite(Resume r, File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(r);
        }
    }

    @Override
    protected Resume doGet(File file) {
        if (!file.exists()) {
            throw new StorageException("File does not exist");
        }

        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("Read operation failed");
        }

    }

    protected Resume doRead(File file) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Reading failed");
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Deleting failed");
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> result = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Files list is absent.");
        }
        for (File file : files) {
            result.add(doGet(file));
        }

        return result;
    }
}
