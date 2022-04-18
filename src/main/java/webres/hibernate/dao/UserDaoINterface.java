package webres.hibernate.dao;

import webres.web.model.User;

import java.util.List;

public interface UserDaoINterface {

    void delete(Long id);
    void update(User user);
    void add(User user);
    List<User> getListUsers();
}
