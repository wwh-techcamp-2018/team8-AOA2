package com.aoa.springwebservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderFormDTO {
    private String name;
    private String phoneNumber_1;
    private String phoneNumber_2;
    private String phoneNumber_3;
    private String pickupTime;
    private List<OrderItemDTO> orderItemDTOs;

    //todo DTO (String pickupTime) > Domain (LocalDateTime pickupTime)시에 참조
    /*
        LocalTime convertedLocalTime = LocalTime.parse(pickupTime, DateTimeFormatter.ofPattern("HH:mm"));
        this.pickupTime = LocalDate.now().atTime(convertedLocalTime);
     */

    public OrderFormDTO(String name, String phoneNumber_1, String phoneNumber_2, String phoneNumber_3, String pickupTime, List<OrderItemDTO> orderItemDTOs) {
        this.name = name;
        this.phoneNumber_1 = phoneNumber_1;
        this.phoneNumber_2 = phoneNumber_2;
        this.phoneNumber_3 = phoneNumber_3;
        this.pickupTime = pickupTime;
        this.orderItemDTOs = orderItemDTOs;
    }
}
