package org.cold92.service;

import org.cold92.bean.UserBean;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    int insertUser(UserBean user);

    int deleteUser(String username);

    int updateUser(UserBean userBean);

    List<UserBean> getAllUsers();

    UserBean getUserByUsername(String username);
}
