package org.cold92;

import org.cold92.service.CityService;
import org.cold92.service.OrderService;
import org.cold92.service.RateService;
import org.cold92.service.UserService;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Autowired
    private CityService cityService;
    @Autowired
    private UserService userService;
    @Autowired
    private RateService rateService;
    @Autowired
    private OrderService orderService;

    @org.junit.Test
    public void test() {
        System.out.println(orderService.getAllOrders());
    }
}
