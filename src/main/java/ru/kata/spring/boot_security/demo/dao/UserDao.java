package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> findAll();

    Optional<User> findOne(long id);

    User findByUsername(String name);

    void save(User user);

    void update(long id, User updeteUser);

    void delete(long id);
}
