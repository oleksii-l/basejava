package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        Assert.assertTrue(storage.size() > 0);
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void get() {
        Resume res = storage.get(UUID_2);
        Assert.assertNotNull(res);
        Assert.assertEquals(UUID_2, res.getUuid());
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void save() {
        storage.save(new Resume(UUID_4));

        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(UUID_4, storage.get(UUID_4).getUuid());
    }

    @Test
    public void update() {
        Resume old = storage.get(UUID_2);

        storage.update(new Resume(UUID_2));

        Resume result = storage.get(UUID_2);
        Assert.assertNotNull(result);
        Assert.assertNotSame(old, result);
    }

    @Test
    public void delete() {
        storage.delete(UUID_2);

        Assert.assertEquals(2, storage.size());
        try {
            storage.get(UUID_2);
            fail("Resume [" + UUID_2 + "] was NOT deleted");
        } catch (NotExistStorageException e){}
    }

    @Test
    public void getAll() {
        Resume[] result = storage.getAll();
        Assert.assertNotNull(result);
        Assert.assertEquals(3, result.length);
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
}