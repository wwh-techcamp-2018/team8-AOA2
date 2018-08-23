package com.aoa.springwebservice.domain;

import java.time.LocalDateTime;

public class Order {

    private User customer;

    private Store store;

    private LocalDateTime createdDate;

    private LocalDateTime pickupTime;

    private int orderTotalPrice;

    private boolean isPickedup;

    //Todo :: 다른 상태 있는지 고려해야함. 미수령/수령이 아니라 취소나 수령대기나 등등등


}
