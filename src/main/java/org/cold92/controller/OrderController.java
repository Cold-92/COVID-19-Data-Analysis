package org.cold92.controller;

import org.cold92.bean.OrderBean;
import org.cold92.handler.MailHandler;
import org.cold92.service.CityService;
import org.cold92.service.OrderService;
import org.cold92.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class OrderController {

    @Autowired
    private MailHandler mailHandler;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CityService cityService;
    @Autowired
    private UserService userService;

    /**
     * 增加用户订阅记录
     * @param cityName
     * @return
     */
    @GetMapping("/insertOrder")
    public void insertOrder(HttpServletRequest request, String cityName) {
        String username = (String) request.getSession().getAttribute("login-username");
        String email = userService.getUserByUsername(username).getEmail();
        OrderBean order = new OrderBean();
        order.setUsername(username);
        order.setEmail(email);
        order.setCity(cityName);
        orderService.insertOrder(order);
    }

    /**
     * 删除用户订阅记录
     * @param username
     */
    @GetMapping("/deleteOrder")
    public void deleteOrder(String username) {
        orderService.deleteOrder(username);
    }
}
