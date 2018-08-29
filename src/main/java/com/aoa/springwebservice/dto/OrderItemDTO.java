package com.aoa.springwebservice.dto;

import com.aoa.springwebservice.domain.Order;
import com.aoa.springwebservice.domain.OrderItem;
import com.aoa.springwebservice.domain.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderItemDTO {
    private long reservationId;
    private int itemCount;

    public OrderItemDTO(long reservationId, int itemCount) {
        this.reservationId = reservationId;
        this.itemCount = itemCount;
    }

    public OrderItem toDomain(Order order, Reservation reservation) {

        return OrderItem.builder().order(order)
                .reservation(reservation)
                .itemCount(this.itemCount)
                .build();
    }
    public static OrderItemDTO createOrderItemDTO(@NotNull OrderItem orderItem) {
        return new OrderItemDTO (orderItem.getReservation().getId(), orderItem.getItemCount());
    }
}
