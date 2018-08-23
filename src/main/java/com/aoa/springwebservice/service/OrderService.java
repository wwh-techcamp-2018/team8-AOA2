package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.*;
import com.aoa.springwebservice.dto.OrderFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

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
                if(!reservation.isPossiblePurchase(orderItemDTO.getItemCount())) {
                    throw new RuntimeException("Cannot buy");
                }
                orderItemDTO.toDomain(order, reservation);
                reservation.orderMenu(orderItemDTO.getItemCount());
            }
        });
        return orderRepositroy.save(order);
    }

}
