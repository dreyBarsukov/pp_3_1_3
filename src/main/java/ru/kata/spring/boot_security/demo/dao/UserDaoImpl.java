package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    public UserDaoImpl() {
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select user from User user", User.class).getResultList();
    }

    @Override
    public User findOne(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id)).orElseThrow();
    }

    @Override
    public User findByUsername(String name) {
        try {
            User user = (User) entityManager.createQuery("select user from User user where user.email = ?1")
                    .setParameter(1, name)
                    .getSingleResult();
            return user;
        } catch (NoResultException e) {
            throw new UsernameNotFoundException("User not found!");
        }
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(long id, User updeteUser) {
        entityManager.merge(updeteUser);
    }

    @Override
    public void delete(long id) {
        entityManager.remove(findOne(id));
    }
}
