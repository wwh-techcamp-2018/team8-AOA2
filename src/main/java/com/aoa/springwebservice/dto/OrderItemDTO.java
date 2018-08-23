package com.aoa.springwebservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDTO {
    private long menuId;
    private int itemCount;
}
