package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.*;
import com.aoa.springwebservice.dto.OrderFormDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepositroy;

    public Order createOrder(Map<Long, Reservation> result, OrderFormDTO orderFormDTO, Order order) {
        //todo : 구매 갯수와 예약 갯수가 일치 (못사는 것에 대한 에러처리)
        orderFormDTO.getOrderItemDTOs().stream().forEach(orderItemDTO -> {
            long id = orderItemDTO.getMenuId();
            Reservation reservation = result.get(id);
            if(result.containsKey(id)) {
                reservation.checkPossiblePurchase(orderItemDTO.getItemCount());
                orderItemDTO.toDomain(order, reservation);
                reservation.orderMenu(orderItemDTO.getItemCount());
            }
        });
        return orderRepositroy.save(order);
    }

}
