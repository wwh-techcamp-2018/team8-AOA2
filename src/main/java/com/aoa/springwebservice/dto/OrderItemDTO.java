package com.aoa.springwebservice.dto;

import com.aoa.springwebservice.domain.Menu;
import com.aoa.springwebservice.domain.Order;
import com.aoa.springwebservice.domain.OrderItem;
import com.aoa.springwebservice.domain.Reservation;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderItemDTO {
    private long reservationId;
    private int itemCount;

    private int itemTotalPrice;
    private long id;
    private Reservation reservation;

    @Builder
    public OrderItemDTO(long reservationId, int itemCount, int itemTotalPrice, long id, Reservation reservation) {
        this.reservationId = reservationId;
        this.itemCount = itemCount;
        this.itemTotalPrice = itemTotalPrice;
        this.id = id;
        this.reservation = reservation;
    }

    public OrderItem toDomain(Order order, Reservation reservation) {

        return OrderItem.builder().order(order)
                .reservation(reservation)
                .itemCount(this.itemCount)
                .build();
    }
    public static OrderItemDTO createOrderItemDTO(@NotNull OrderItem orderItem) {
        return new OrderItemDTO (orderItem.getReservation().getId(), orderItem.getItemCount(), orderItem.getItemTotalPrice(), orderItem.getId(), orderItem.getReservation());
    }
}
