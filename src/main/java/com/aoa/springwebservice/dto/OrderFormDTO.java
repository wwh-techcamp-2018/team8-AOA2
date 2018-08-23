package com.aoa.springwebservice.dto;

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
    private List<OrderItemDTO> orderItemDTOs;
    private LocalDateTime pickupTime;

}
