package com.aoa.springwebservice.dto;

import com.aoa.springwebservice.domain.Reservation;
import com.aoa.springwebservice.domain.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor
public class ReservatioDTO {
    private long menuId;
    private int maxCount;
    private int personalMaxCount;
    @Builder
    public ReservatioDTO(long menuId, int maxCount, int personalMaxCount) {
        this.menuId = menuId;
        this.maxCount = maxCount;
        this.personalMaxCount = personalMaxCount;
    }

    public Reservation toDomain(Store store) {
        return Reservation.builder()
                .maxCount(maxCount)
                .personalMaxCount(personalMaxCount)
                .store(store)
                // todo refactoring & orElse //store.getMenuById(menuId))
                .menu(store.getMenus().stream().filter(x-> x.getId() == menuId).findFirst().get())
                .openDate(LocalDate.now())
                .build();
    }

    ReservationFormDTO.toDomain();
}
