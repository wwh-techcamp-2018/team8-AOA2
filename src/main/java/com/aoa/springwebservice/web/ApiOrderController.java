package com.aoa.springwebservice.web;

import com.aoa.springwebservice.dto.OrderFormDTO;
import com.aoa.springwebservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class ApiOrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/temp")
    public String tempCreateOrder(@RequestBody OrderFormDTO orderFormDTO){
        log.debug("orderFormDTO {}", orderFormDTO);
        return "OK";
    }

}
