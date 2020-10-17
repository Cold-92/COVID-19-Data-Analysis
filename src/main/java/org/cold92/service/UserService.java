package org.cold92.service;

import org.cold92.bean.UserBean;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    int insertUser(UserBean user);

    UserBean getUserByUsername(String username);
}
