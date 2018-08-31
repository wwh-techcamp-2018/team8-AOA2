package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.*;
import com.aoa.springwebservice.dto.OrderFormDTO;
import com.aoa.springwebservice.exception.CustomerOrderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepositroy;

    public Order createOrder(Map<Long, Reservation> result, OrderFormDTO orderFormDTO, Order order) {
        //todo : 구매 갯수와 예약 갯수가 일치 (못사는 것에 대한 에러처리)
        orderFormDTO.getOrderItemDTOs().stream().forEach(orderItemDTO -> {
            long id = orderItemDTO.getReservationId();
            Reservation reservation = result.get(id);
            if(result.containsKey(id)) {
                reservation.checkPossiblePurchase(orderItemDTO.getItemCount());
                orderItemDTO.toDomain(order, reservation);
                reservation.orderMenu(orderItemDTO.getItemCount());
            }
        });
        return orderRepositroy.save(order);
    }

    public List<Order> selectOrders(Store store, LocalDateTime lastDay) {
        if(!store.isOpen()) throw new CustomerOrderException("진행 중인 예약이 없습니다");
        return orderRepositroy.findByStoreAndPickupTimeAfterOrderByPickupTime(store, lastDay);
    }

    public Order updateIsPickedupStatus(Store store, long orderId, Order order) {
        Order returnOrder = orderRepositroy.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order가 없습니다."));
        if(!returnOrder.hasSameStore(store)){
            throw new RuntimeException("주문 상태 변경 권한이 없습니다");
        }
        returnOrder.setIsPickedupByOrder(order);
        return orderRepositroy.save(returnOrder);
    }

    public Order getOrder(long orderId) {
        return orderRepositroy.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order가 없습니다."));
    }

}
