package com.aoa.springwebservice.web;

import com.aoa.springwebservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class ApiOrderController {

    @Autowired
    private OrderService orderService;



}
