package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.User;

import java.util.List;

public interface UserRepository {
    User findByLogin(String login);
    void save(User user);
    User findById(long id);
    User findByEmail(String email);
    List<User> findAll();
    long generateNextId();
    long userCount();
}
