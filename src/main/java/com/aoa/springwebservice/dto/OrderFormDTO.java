package com.aoa.springwebservice.dto;

import com.aoa.springwebservice.domain.Customer;
import com.aoa.springwebservice.domain.Order;
import com.aoa.springwebservice.domain.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderFormDTO {
    private String name;
    private String phoneNumber_1;
    private String phoneNumber_2;
    private String phoneNumber_3;
    private LocalDateTime pickupTime;
    private List<OrderItemDTO> orderItemDTOs;

    @Builder
    public OrderFormDTO(String name, String phoneNumber_1, String phoneNumber_2, String phoneNumber_3, LocalDateTime pickupTime, List<OrderItemDTO> orderItemDTOs) {
        this.name = name;
        this.phoneNumber_1 = phoneNumber_1;
        this.phoneNumber_2 = phoneNumber_2;
        this.phoneNumber_3 = phoneNumber_3;
        this.pickupTime = pickupTime;
        this.orderItemDTOs = orderItemDTOs;
    }

    public Order toDomain(Store store) {
        return Order.builder()
                .customer(Customer.builder().name(name).phoneNumber(phoneNumber_1 + phoneNumber_2 + phoneNumber_3).build())
                .pickupTime(pickupTime)
                .store(store)
                .build();
    }
}
