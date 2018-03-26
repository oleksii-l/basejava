package ru.javawebinar.basejava.storage;

import java.io.File;

public class FileStorage extends AbstractFileStorage {


    protected FileStorage(File directory) {
        super(directory);
    }
}
