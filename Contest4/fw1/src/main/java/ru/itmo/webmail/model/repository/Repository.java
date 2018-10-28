package ru.itmo.webmail.model.repository;

import java.util.List;

public interface Repository<T> {

    void save(T item);

    List<T> findAll();

    long countItems();

}
