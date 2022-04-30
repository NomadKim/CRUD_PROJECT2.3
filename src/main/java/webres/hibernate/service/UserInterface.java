package webres.hibernate.service;

import webres.web.model.User;
import java.util.List;

public interface UserInterface {

    void delete(Long id);
    void update(User user);
    void add(User user);
    List<User> getListUsers();

}
