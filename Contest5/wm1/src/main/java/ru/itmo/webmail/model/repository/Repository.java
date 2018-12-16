package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Item;

import java.util.List;

public interface Repository<T extends Item> {
    void save(T item, AttributePair<?> ... values);
    void update(long id, AttributePair<?> value);
    List<T> findAll();
    T findByAttributes(AttributePair<?> ... values);
    T findOccur(AttributePair<?> ... values);
}
