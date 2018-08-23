package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.Order;
import com.aoa.springwebservice.domain.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepositroy;

    public Order save(Order order) {
        return orderRepositroy.save(order);
    }

}
