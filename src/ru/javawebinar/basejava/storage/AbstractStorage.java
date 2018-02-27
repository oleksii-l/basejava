package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract void doUpdate(SK searchKey, Resume r);

    protected abstract void doSave(SK searchKey, Resume r);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract List<Resume> doCopyAll();


    @Override
    public abstract void clear();

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        SK searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(searchKey, r);
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        SK searchKey = getNotExistingSearchKey(r);
        doSave(searchKey, r);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = doCopyAll();
        list.sort(new ResumeComparator());
        return list;
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);

        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistingSearchKey(Resume r) {
        SK searchKey = getSearchKey(r.getUuid());

        if (isExist(searchKey)) {
            LOG.warning("Resume " + r.getUuid() + " already exist");
            throw new ExistStorageException(r.getUuid());
        }
        return searchKey;
    }

    static class ResumeComparator implements Comparator<Resume> {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getFullName().compareTo(o2.getFullName());
        }
    }

}
