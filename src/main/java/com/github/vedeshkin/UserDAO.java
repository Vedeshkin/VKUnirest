package com.github.vedeshkin;

import java.util.List;

/**
 * Created by vvedeshkin on 4/12/2017.
 */
public interface UserDAO {
    void storeUser(UserBean user);
    void storeUsers(List<UserBean> users);
    List<UserBean> getAllUsers();
    void deleteAllUsers();
}
