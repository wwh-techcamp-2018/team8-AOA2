package com.aoa.springwebservice.domain;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Reservation {

    private static boolean ACTIVATED = true;
    private static boolean DEACTIVATED = false;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Menu menu;

    @ManyToOne
    @ToString.Exclude
    private Store store;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "maxCount", column = @Column(nullable = false)),
            @AttributeOverride(name = "personalMaxCount", column = @Column(nullable = false))
    })
    private MaxCount maxCount;

    private LocalDate openDate;
    //todo menu deleted 상태

    private int availableCount;

    @Builder
    public Reservation(Menu menu, Store store, MaxCount maxCount, LocalDate openDate) {
        this.menu = menu;
        //hint  menu.changeTodayMenu(, );
        this.store = store;
        this.maxCount = maxCount;
        this.openDate = openDate;
        this.availableCount = maxCount.getMaxCount();
        //hint store.addReservation(this);
    }

    public void regist() {
        menu.setUpLastUsedStatus(maxCount);
    }

    public void orderMenu(int count) {
        this.availableCount -= count;
    }

    public int calculatePrice(int itemCount) {
        return this.menu.calculatePrice(itemCount);
    }
}
