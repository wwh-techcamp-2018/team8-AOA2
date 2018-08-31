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
    private String menuName;

    @Builder
    public OrderItemDTO(long reservationId, int itemCount, int itemTotalPrice, long id, String menuName) {
        this.reservationId = reservationId;
        this.itemCount = itemCount;
        this.itemTotalPrice = itemTotalPrice;
        this.id = id;
        this.menuName = menuName;
    }

    public OrderItem toDomain(Order order, Reservation reservation) {

        return OrderItem.builder().order(order)
                .reservation(reservation)
                .itemCount(this.itemCount)
                .build();
    }
    public static OrderItemDTO createOrderItemDTO(@NotNull OrderItem orderItem) {
        Reservation reservation = orderItem.getReservation();

        return new OrderItemDTO (reservation.getId(), orderItem.getItemCount(), orderItem.getItemTotalPrice(), orderItem.getId(),reservation.getMenu().getName());
    }

}
