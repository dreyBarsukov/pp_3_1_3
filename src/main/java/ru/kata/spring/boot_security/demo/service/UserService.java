package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findOne(long id);

    void save(User user, List<String> roles);

    void update(long id, User updeteUser, List<String> roles);

    void delete(long id);
}
