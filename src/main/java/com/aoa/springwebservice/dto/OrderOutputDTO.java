package com.aoa.springwebservice.dto;

import com.aoa.springwebservice.converter.NumberConverter;
import com.aoa.springwebservice.domain.Customer;
import com.aoa.springwebservice.domain.Order;
import com.aoa.springwebservice.domain.User;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
@Getter @NoArgsConstructor
public class OrderOutputDTO {
    private long id;
//    private String name;
//    private String phoneNumber;
    @JsonUnwrapped
    private Customer customer;
    private int orderTotalPrice;

    private boolean isPickedup;

    private String pickupTime;
    private String hourToPickup;
    private String minuteToToPickup;

    private boolean pickedUp;
    private List<OrderItemDTO> orderItems;
    @Builder
    public OrderOutputDTO(long id, Customer customer, int orderTotalPrice, LocalDateTime pickupTime, boolean isPickedUp, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.customer = customer;
        this.orderTotalPrice = orderTotalPrice;
        this.pickupTime =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(pickupTime);
        this.pickedUp = isPickedUp;
        this.orderItems = orderItems;
        this.hourToPickup =  pickupTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH"));
        this.minuteToToPickup = pickupTime.toLocalTime().format(DateTimeFormatter.ofPattern("mm"));
    }

    public static OrderOutputDTO createOrderOutputDTO(@NotNull Order order){
        return OrderOutputDTO.builder()
                .id(order.getId())
                .customer(order.getCustomer())
                .orderTotalPrice(order.getOrderTotalPrice())
                .pickupTime(order.getPickupTime())
                .isPickedUp(order.getIsPickedup())
                .orderItems(order.getOrderItems()
                                .stream()
                                .map((x) ->OrderItemDTO.createOrderItemDTO(x))
                                .collect(Collectors.toList()))
                .build();


    }
}
