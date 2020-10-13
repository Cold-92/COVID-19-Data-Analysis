package org.cold92.service;

import org.cold92.bean.UserBean;

public interface UserService {

    int insertUser(UserBean user);

    UserBean getUserByName(String userName);
}
