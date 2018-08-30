package com.aoa.springwebservice.dto;

import com.aoa.springwebservice.domain.Reservation;
import com.aoa.springwebservice.domain.Store;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @NoArgsConstructor @ToString
public class ReservationFormDTO {
    // 역정규화된 레코드에서 중복되는 컬럼은 하나로 받고, 중복아닌 컬럼은 자료구조로
    private String openDate;

    @Min(0) @Max(24)
    private int hourToClose;

    @Min(0) @Max(59)
    private int minuteToClose;

    private List<ReservationDTO> reservationDTOs = new ArrayList<>();

    @Builder
    public ReservationFormDTO(String openDate, @Min(0) @Max(24) int hourToClose, @Min(0) @Max(59) int minuteToClose, List<ReservationDTO> reservationDTOs) {
        this.openDate = openDate;
        this.hourToClose = hourToClose;
        this.minuteToClose = minuteToClose;
        this.reservationDTOs = reservationDTOs;
    }

    public LocalDateTime generateTimeToClose() {
        LocalDateTime timeToClose = LocalDate.now().plusDays(1).atTime(hourToClose, minuteToClose);
        return timeToClose;
    }

    public List<Reservation> generateReservations(Store store) {
        return reservationDTOs.stream()
                .map(reservationDTO -> reservationDTO.toDomain(store))
                .collect(Collectors.toList());
    }



//
//    public List<Reservation> toDomain(Store store){//, Map<Long, Menu> menu){
//        return null;
//    }
//
//    public Store fillReservations(Store store){
//
//        reservatioDTOs.stream().forEach(x -> { x.toDomain(store);  store.addReservation(res)});
//        return store;
//    }
//    public List<Reservation> toDomain(Store store){
//        reservatioDTOs.stream().forEach(x -> {x.toDomain(store);;
//        return store;
//        // service
//            // store.set...(dto)
//            // store.addReservations(List<Reservation> ..)
//            //storeRepository.save()
//    }
}