package com.aoa.springwebservice.dto;

import com.aoa.springwebservice.domain.Order;
import com.aoa.springwebservice.domain.OrderItem;
import com.aoa.springwebservice.domain.Reservation;
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

    public OrderItemDTO(long menuId, int itemCount) {
        this.menuId = menuId;
        this.itemCount = itemCount;
    }

    public OrderItem toDomain(Order order, Reservation reservation) {
        return OrderItem.builder().order(order)
                .reservation(reservation)
                .itemCount(this.itemCount)
                .build();
    }
}
