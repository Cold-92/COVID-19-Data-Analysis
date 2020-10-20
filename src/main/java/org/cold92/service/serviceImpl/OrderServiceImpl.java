package org.cold92.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.cold92.bean.OrderBean;
import org.cold92.mapper.OrderMapper;
import org.cold92.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int insertOrder(OrderBean order) {
        return orderMapper.insert(order);
    }

    @Override
    public int deleteOrder(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", username);
        return orderMapper.delete(wrapper);
    }

    @Override
    public List<OrderBean> getAllOrders() {
        return orderMapper.selectList(null);
    }
}
