package org.cold92.controller;

import org.cold92.bean.UserBean;
import org.cold92.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    @GetMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @PostMapping("/register")
    public String register(UserBean user) {
        if (user != null) {
            //进行加密
            BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword().trim()));
            userService.insertUser(user);
        }
        return "redirect:/toLogin";
    }

    /**
     * 注销用户
     * @return
     */
    @GetMapping("/cancelUser")
    public String cancelUser(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("login-username");
        userService.deleteUser(username);
        return "redirect:/toLogin";
    }

    /**
     * 删除用户
     * @param username
     * @return
     */
    @GetMapping("/deleteUser")
    public String deleteUser(String username) {
        userService.deleteUser(username);
        return "redirect:/toLogin";
    }

    /**
     * 更改用户
     * @param password
     * @param email
     */
    @PostMapping("/updateUser")
    public String updateUser(HttpServletRequest request, String password, String email) {
        String username =  (String) request.getSession().getAttribute("login-username");
        UserBean user = userService.getUserByUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        userService.updateUser(user);
        return "redirect:/toLogin";
    }

    /**
     * 获取个人信息
     * @return
     */
    @GetMapping("/getUserByUsername")
    public String getUserByUsername(Model model, HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("login-username");
        UserBean user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "redirect:/toLogin";
    }

    /**
     * 获取所有用户信息
     * @return
     */
    @GetMapping("/getAllUsers")
    public String getAllUsers(Model model) {
        List<UserBean> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        return "redirect:/toLogin";
    }
}
