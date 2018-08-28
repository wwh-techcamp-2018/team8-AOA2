package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.Order;
import com.aoa.springwebservice.dto.ExtendableDTO;
import com.aoa.springwebservice.dto.OrderFormDTO;
import com.aoa.springwebservice.service.OrderService;
import com.aoa.springwebservice.service.ReservationService;
import com.aoa.springwebservice.service.StoreService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiOrderController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/stores/{storeId}/orders")
    public Object makePayment(@PathVariable long storeId, @RequestBody OrderFormDTO orderFormDTO) {
        Order order = orderFormDTO.toDomain(storeService.getStoreById(storeId));
        //todo : parameter 3개...refactor 필요
        order = orderService.createOrder(reservationService.getTodayReservations(storeId), orderFormDTO, order);
        //todo refactor - dto? or domain
        ExtendableDTO dto = new ExtendableDTO();
        dto.add("name", order.getCustomer().getName());
        dto.add("phoneNumber", order.getCustomer().getPhoneNumber());
        dto.add("pickupTime", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(order.getPickupTime()));
        dto.add("orderTotalPrice", order.getOrderTotalPrice());
        return dto;
    }


}
