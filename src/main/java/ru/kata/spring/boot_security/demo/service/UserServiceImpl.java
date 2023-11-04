package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserServiceImpl() {
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User findOne(long id) {
        return userDao.findOne(id);
    }

    @Transactional
    @Override
    public void save(User user, List<String> roles) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add(roleService.findByName(role));
        }
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        user.setRoles(roleSet);
        userDao.save(user);
    }

    @Transactional
    @Override
    public void update(long id, User updeteUser, List<String> roles) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add(roleService.findByName(role));
        }
        String encode = passwordEncoder.encode(updeteUser.getPassword());
        updeteUser.setRoles(roleSet);
        updeteUser.setPassword(encode);
        userDao.update(id, updeteUser);
    }

    @Transactional
    @Override
    public void delete(long id) {
        userDao.delete(id);
    }
}
