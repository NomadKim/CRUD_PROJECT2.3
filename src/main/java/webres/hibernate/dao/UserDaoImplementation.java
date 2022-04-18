package webres.hibernate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import webres.web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class UserDaoImplementation implements UserDaoINterface{

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Override
    public void delete(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user2 = entityManager.find(User.class, id);
        entityManager.remove(user2);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user2 = entityManager.find(User.class, user.getId());
        entityManager.detach(user2);
        user2.setFirstName(user.getFirstName());
        user2.setLastName(user.getLastName());
        user2.setEmail(user.getEmail());
        entityManager.getTransaction().begin();
        entityManager.merge(user2);
        entityManager.getTransaction().commit();
    }

    @Override
    public void add(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<User> getListUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery("select users from User users").
                getResultList();
    }

}
