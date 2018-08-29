package com.aoa.springwebservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @ToString.Exclude
    @JsonIgnore
    private Order order;

    @ManyToOne
    private Reservation reservation;

    private int itemCount;

    private int itemTotalPrice;

    @Builder
    public OrderItem(Order order, Reservation reservation, int itemCount) {
        this.order = order;
        this.reservation = reservation;
        this.itemCount = itemCount;
        this.itemTotalPrice = reservation.calculatePrice(itemCount);
        order.addOrderItem(this);
    }
}