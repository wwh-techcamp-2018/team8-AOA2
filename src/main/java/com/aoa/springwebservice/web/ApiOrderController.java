package com.aoa.springwebservice.web;

import com.aoa.springwebservice.RestResponse;

import com.aoa.springwebservice.domain.Order;
import com.aoa.springwebservice.dto.OrderFormDTO;
import com.aoa.springwebservice.service.OrderService;
import com.aoa.springwebservice.service.ReservationService;
import com.aoa.springwebservice.service.StoreService;

import com.aoa.springwebservice.dto.OrderFormDTO;
import com.aoa.springwebservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void makePayment(@PathVariable long storeId, @RequestBody OrderFormDTO orderFormDTO) {
        Order order = orderFormDTO.toDomain(storeService.getStoreById(storeId));
        //todo : parameter 3개...refactor 필요
        orderService.createOrder(reservationService.getTodayReservations(storeId), orderFormDTO, order);
    }

    @PostMapping("/orders/{orderId}")
    public Order updateIsPickedupStatus(@PathVariable long orderId, @RequestBody Order order){
        log.debug("pickedupStatus : {}", order);
        return orderService.updateIsPickedupStatus(orderId, order);
    }

    @PostMapping("/temp")
    public RestResponse<RestResponse.RedirectData> tempCreateOrder(@RequestBody OrderFormDTO orderFormDTO){
        log.debug("orderFormDTO {}", orderFormDTO);
        return RestResponse.ofRedirectResponse("/orders/1/result","OK");
    }

}
