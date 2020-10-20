package org.cold92.service;

import org.cold92.bean.OrderBean;

import java.util.List;

public interface OrderService {

    int insertOrder(OrderBean order);

    int deleteOrder(String username);

    List<OrderBean> getAllOrders();
}
