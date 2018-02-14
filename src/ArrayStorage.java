/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[3];
    int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {

        if (isPresent(r.uuid)) {
            System.out.println("ERROR: Resume is already present");
            return;
        }

        if (size == storage.length) {
            System.out.println("ERROR: storage is FULL");
        }

        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                size++;
                break;
            }
        }
    }


    void update(Resume r) {
        if (!isPresent(r.uuid)) {
            System.out.println("ERROR: Resume is NOT present");
            return;
        }

        for (int i = 0; i < size; i++) {
            if (storage[i] != null && storage[i].uuid.equals(r.uuid)) {
                storage[i] = r;
            }
        }

    }

    Resume get(String uuid) {
        if (!isPresent(uuid)) {
            System.out.println("ERROR: Resume is NOT present");
            return null;
        }

        for (int i = 0; i < size; i++) {
            if (storage[i] != null && storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }

        return null;
    }

    void delete(String uuid) {
        if (!isPresent(uuid)) {
            System.out.println("ERROR: Resume is NOT present");
            return;
        }

        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;

                break;
            }
        }

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

    private boolean isPresent(String uuid) {
        for(int i=0; i<size; i++) {
            if (storage[i].uuid.equals(uuid)){
                return true;
            }
        }
        return false;
    }
}
