package com.aoa.springwebservice.service.support;

import com.aoa.springwebservice.domain.Reservation;
import com.aoa.springwebservice.domain.ReservationRepository;
import com.aoa.springwebservice.domain.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Component(value = "lastReservation")
public class LastReservationSelector implements ReservationSelector{

    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public List<Reservation> select(Store store) {
        //todo Exception 날리기 + Refactoring
        LocalDate lastDate = reservationRepository
                .findFirstByStoreAndOpenDateBeforeOrderByOpenDateDesc(store, store.getTimeToClose().toLocalDate().minusDays(1))
                .orElseThrow((() -> new EntityNotFoundException("직전 예약이 없어요.")))
                .getOpenDate();

        return reservationRepository.findAllByStoreAndOpenDate(store, lastDate);
    }

}