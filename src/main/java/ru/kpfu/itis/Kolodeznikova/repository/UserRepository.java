package ru.kpfu.itis.Kolodeznikova.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.Kolodeznikova.model.User;

import java.util.List;

@Repository
public class UserRepository {

    private final SessionFactory sessionFactory;

    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from User", User.class)
                .list();
    }

    public void save(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    public User findById(Long id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

}
