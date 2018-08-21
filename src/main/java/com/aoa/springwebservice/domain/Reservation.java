package com.aoa.springwebservice.domain;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @NoArgsConstructor @ToString
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Menu menu;

    @ManyToOne @ToString.Exclude
    private Store store;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="maxCount", column=@Column(nullable = false)),
            @AttributeOverride(name="personalMaxCount", column=@Column(nullable = false))
    })
    private MaxCount maxCount;

    private LocalDate openDate;
    //todo menu deleted 상태
    @Builder
    public Reservation(Menu menu, Store store, MaxCount maxCount, LocalDate openDate) {
        this.menu = menu;
      //hint  menu.changeTodayMenu(, );
        this.store = store;
        this.maxCount = maxCount;
        this.openDate = openDate;
       //hint store.addReservation(this);
    }

    public void update() {
        menu.changeTodayMenu(maxCount);
    }
}
