package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional(readOnly = true)
    public User findOne(long id) {
        return userDao.findOne(id);
    }

    @Override
    public User findByName(String name) {
        return userDao.findByUsername(name);
    }

    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Transactional
    public void update(long id, User updeteUser) {
        userDao.update(id, updeteUser);
    }

    @Transactional
    public void delete(long id) {
        userDao.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return (UserDetails) findByName(username);
    }
}
