/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        storage = new Resume[10000];
    }

    void save(Resume r) {

        for(int i=0; i<storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }
    }

    Resume get(String uuid) {

        for(int i=0; i<storage.length; i++) {
            if ( storage[i] != null && storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }

        return null;
    }

    void delete(String uuid) {

        for(int i=0; i<storage.length; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                for (int j=i+1; j<storage.length && storage[j] != null; j++) {
                    storage[j-1] = storage[j];
                    storage[j] = null;
                }

                break;
            }
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {

        Resume[] result = new Resume[size()];
        for (int i=0; i<size(); i++){
            result[i] = storage[i];
        }

        return result;
    }

    int size() {
        for(int i=0; i<storage.length; i++) {
            if (storage[i] == null) {
                return i;
            }
        }

        return storage.length;
    }
}
