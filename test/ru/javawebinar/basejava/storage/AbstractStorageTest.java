package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    public static final String FULL_NAME_1 = "Petrov";
    public static final String FULL_NAME_2 = "Sidorov";
    public static final String FULL_NAME_3 = "Ivanov";
    public static final String FULL_NAME_4 = "Aaaa";    //first element after sorting

    private static final Resume RESUME_1 = new Resume(UUID_1, FULL_NAME_1);
    private static final Resume RESUME_2 = new Resume(UUID_2, FULL_NAME_2);
    private static final Resume RESUME_3 = new Resume(UUID_3, FULL_NAME_3);
    private static final Resume RESUME_4 = new Resume(UUID_4, FULL_NAME_4);


    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        assertTrue(storage.size() > 0);
        storage.clear();
        assertSize(0);
    }

    @Test
    public void get() {
        Resume res = storage.get(UUID_2);
        assertNotNull(res);
        assertSame(RESUME_2, res);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);

        assertSize(4);
        assertSame(RESUME_4, storage.get(UUID_4));
    }

    @Test
    public void update() {
        Resume old = storage.get(UUID_2);

        storage.update(new Resume(UUID_2, FULL_NAME_2));

        Resume result = storage.get(UUID_2);
        assertNotNull(result);
        assertNotSame(old, result);
    }

    @Test
    public void delete() {
        storage.delete(UUID_2);

        assertSize(2);
        try {
            storage.get(UUID_2);
            fail("Resume [" + UUID_2 + "] was NOT deleted");
        } catch (NotExistStorageException e) {
        }
    }

    @Test
    public void getAll() {
        Resume[] result = storage.getAll();
        assertNotNull(result);
        assertEquals(3, result.length);
    }

    @Test
    public void getAllSorted() {
        List<Resume> result = storage.getAllSorted();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(FULL_NAME_3, result.get(0).getFullName());
        assertEquals(FULL_NAME_1, result.get(1).getFullName());
        assertEquals(FULL_NAME_2, result.get(2).getFullName());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy", "dummy"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_2, FULL_NAME_2));
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}