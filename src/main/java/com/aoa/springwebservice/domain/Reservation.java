package com.aoa.springwebservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter @NoArgsConstructor @ToString
public class Reservation {
    private Menu menu;
    private Store store;
    private int maxCount;
    private int personalMaxCount;
    private LocalDate openDate;
    @Builder
    public Reservation(Menu menu, Store store, int maxCount, int personalMaxCount, LocalDate openDate) {
        this.menu = menu;
        menu.changeTodayMenu(, );
        this.store = store;
        this.maxCount = maxCount;
        this.personalMaxCount = personalMaxCount;
        this.openDate = openDate;
        store.addReservation(this);
    }

    public void update() {
        menu.changeTodayMenu(maxCount, personalMaxCount);
    }

    public boolean isValidToOpen() {
        if(maxCount < personalMaxCount) return false;
        return true;
    }
}
