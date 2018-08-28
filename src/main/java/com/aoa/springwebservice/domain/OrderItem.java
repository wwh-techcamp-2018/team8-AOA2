package com.aoa.springwebservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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