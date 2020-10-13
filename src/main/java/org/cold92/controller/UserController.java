package org.cold92.controller;

import org.cold92.bean.UserBean;
import org.cold92.service.UserService;
import org.cold92.util.JasyptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/toRegister")
    public String toRegister() {
        return "register.html";
    }

    @PostMapping("/register")
    public String register(UserBean user) {
        if (user != null) {
            // 加密密码
            user.setPassword(JasyptUtil.encryptPassword(user.getPassword()));
            userService.insertUser(user);
        }
        return "login.html";
    }

    @GetMapping("/toLogin")
    public String toLogin() {
        return "login.html";
    }

    @PostMapping("/login")
    public String login(String userName, String password, HttpSession session) {
        if (userName == null || password == null) {
            return "login.html";
        }
        UserBean user = userService.getUserByName(userName);
        if (user != null && password.equals(JasyptUtil.decyptPassword(user.getPassword()))) {
            // 存储登录状态
            session.setAttribute("loginUser", userName);
            return "redirect:/index";
        }
        return "login.html";
    }
}
