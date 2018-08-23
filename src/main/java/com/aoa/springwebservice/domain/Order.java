package com.aoa.springwebservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Order {

    private User customer;

    private Store store;

    private LocalDateTime createdDate;

    private LocalDateTime pickupTime;

    private int orderTotalPrice;

    private boolean isPickedup;

    //Todo :: OrderBy
    private List<OrderItem> orderItems;

    //Todo :: 다른 상태 있는지 고려해야함. 미수령/수령이 아니라 취소나 수령대기나 등등등


}
