package com.companyname.dirtylibs.service;

import com.companyname.dirtylibs.persistence.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    public User save(User user) {
        return entityManager.merge(user);
    }

    public void delete(User user) {
        entityManager.remove(user);
    }

    public List<User> findByIds(List<Integer> userIds) {
        return entityManager.createQuery("select u from User u where u.id in (:ids)", User.class).setParameter("ids", userIds).getResultList();
    }

    public List<User> findByUsernames(List<String> usernames) {
        return entityManager.createQuery("select u from User u where u.username in (:usernames)", User.class).setParameter("usernames", usernames).getResultList();
    }

    public User findByUsername(String name) {
        return entityManager.createQuery("select u from User u where u.username = (:username)", User.class).setParameter("username", name).getSingleResult();
    }
}
