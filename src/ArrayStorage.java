/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {

        if (getIndexOf(r.uuid) != -1) {
            System.out.println("ERROR: Resume is already present");
            return;
        }

        if (size == storage.length) {
            System.out.println("ERROR: storage is FULL");
        }

        storage[size] = r;
        size++;
    }


    void update(Resume r) {
        int index = getIndexOf(r.uuid);

        if (index == -1) {
            System.out.println("ERROR: Resume is NOT present");
            return;
        }

        storage[index] = r;

    }

    Resume get(String uuid) {
        int index = getIndexOf(uuid);

        if (index == -1) {
            System.out.println("ERROR: Resume is NOT present");
            return null;
        }

        return storage[index];
    }

    void delete(String uuid) {
        int index = getIndexOf(uuid);

        if (index == -1) {
            System.out.println("ERROR: Resume is NOT present");
            return;
        }

        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {

        Resume[] result = new Resume[size()];
        System.arraycopy(storage, 0, result, 0, size);

        return result;
    }

    int size() {
        return size;
    }

    private int getIndexOf(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
