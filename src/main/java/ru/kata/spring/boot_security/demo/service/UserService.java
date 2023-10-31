package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findOne(long id);

    User findByName(String name);

    void save(User user);

    void update(long id, User updeteUser);

    void delete(long id);
}
