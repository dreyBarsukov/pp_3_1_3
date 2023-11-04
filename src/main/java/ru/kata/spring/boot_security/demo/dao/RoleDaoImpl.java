package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    public RoleDaoImpl() {
    }

    @Override
    public Role findByName(String name) {
        try {
            Role role = (Role) entityManager.createQuery("select role from Role role where role.name = ?1")
                    .setParameter(1, name)
                    .getSingleResult();
            return role;
        } catch (NoResultException e) {
            throw new UsernameNotFoundException("Role not found!");
        }
    }

    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("select role from Role role", Role.class).getResultList();
    }

    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }
}
