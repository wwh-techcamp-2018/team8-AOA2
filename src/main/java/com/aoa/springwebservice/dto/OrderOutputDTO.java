package com.aoa.springwebservice.dto;

import com.aoa.springwebservice.converter.NumberConverter;
import com.aoa.springwebservice.domain.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
@Getter @NoArgsConstructor
public class OrderOutputDTO {
    private long id;
    private String name;
    private String phoneNumber;
    private int orderTotalPrice;
    private String pickupTime;
    private List<OrderItemDTO> orderItems;
    @Builder
    public OrderOutputDTO(long id, String name, String phoneNumber, int orderTotalPrice, String pickupTime, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.orderTotalPrice = orderTotalPrice;
        this.pickupTime = pickupTime;
        this.orderItems = orderItems;
    }

    public static OrderOutputDTO createOrderOutputDTO(@NotNull Order order){
        return OrderOutputDTO.builder()
                .id(order.getId())
                .name(order.getCustomer().getName())
                .phoneNumber(NumberConverter.formatPhoneNumber(order.getCustomer().getPhoneNumber()))
                .orderTotalPrice(order.getOrderTotalPrice())
                .pickupTime( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(order.getPickupTime()))
                .orderItems(order.getOrderItems()
                                .stream()
                                .map((x) ->OrderItemDTO.createOrderItemDTO(x))
                                .collect(Collectors.toList()))
                .build();


    }
}
