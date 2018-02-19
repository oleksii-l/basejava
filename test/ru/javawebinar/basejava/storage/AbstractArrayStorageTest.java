package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_4 = new Resume(UUID_4);



    public AbstractArrayStorageTest(Storage storage) {
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

        storage.update(new Resume(UUID_2));

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
        } catch (NotExistStorageException e){}
    }

    @Test
    public void getAll() {
        Resume[] result = storage.getAll();
        assertNotNull(result);
        assertEquals(3, result.length);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_2));
    }

    @Test(expected = StorageException.class)
    public void saveOverFlow() {
        try {
            for (int i = 3; i < storage.capacity(); i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail();
        }

        storage.save(new Resume());
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}