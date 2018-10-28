package ru.itmo.webmail.model.repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRepository<T> implements Repository {

    private static final File tmpDir = new File(System.getProperty("java.io.tmpdir"));
    protected List<T> collection;

    public AbstractRepository() {
        try {
            //noinspection unchecked
            collection = (List<T>) new ObjectInputStream(
                    new FileInputStream(new File(tmpDir, getClass().getSimpleName()))).readObject();
        } catch (Exception e) {
            collection = new ArrayList<>();
        }
    }

    @Override
    public void save(Object item) {
        collection.add((T)item);

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(new File(tmpDir, getClass().getSimpleName())));
            objectOutputStream.writeObject(collection);
            objectOutputStream.close();
        } catch (Exception e) {
            throw new RuntimeException("Can't save item.", e);
        }
    }

    @Override
    public List<T> findAll() {
        return collection;
    }

    @Override
    public long countItems() {
        return collection.size();
    }
}
