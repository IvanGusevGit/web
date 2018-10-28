package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.repository.AbstractRepository;
import ru.itmo.webmail.model.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository{

    private long nextId;

    public UserRepositoryImpl() {
        super();
        nextId = collection.size() + 1;
    }

    @Override
    public long generateNextId() {
        return nextId++;
    }


    @Override
    public long userCount() {
        return countItems();
    }


    @Override
    public User findByLogin(String login) {
        return collection.stream().filter(user -> user.getLogin().equals(login)).findFirst().orElse(null);
    }

    @Override
    public void save(User user) {
        super.save(user);
    }


    @Override
    public User findById(long id) {
        return collection.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return collection.stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
    }
}
