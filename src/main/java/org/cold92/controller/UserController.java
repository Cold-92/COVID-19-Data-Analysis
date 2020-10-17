package org.cold92.controller;

import org.cold92.bean.UserBean;
import org.cold92.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String register(UserBean user) {
        if (user != null) {
            //进行加密
            BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword().trim()));
            System.out.println(user);
            userService.insertUser(user);
        }
        return "redirect:/toLogin";
    }

    @GetMapping("/toLogin")
    public String toLogin() {
        return "login";
    }
}
