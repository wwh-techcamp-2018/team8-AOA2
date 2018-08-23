package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.Reservation;
import com.aoa.springwebservice.domain.ReservationRepository;
import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.domain.StoreRepository;
import com.aoa.springwebservice.dto.ReservationDTO;
import com.aoa.springwebservice.dto.ReservationFormDTO;
import com.aoa.springwebservice.service.support.ReservationSelector;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private BeanFactory beanFactory;

    private static final String SELECTOR_POST_FIX = "Reservation";

    @Transactional
    public void createReservation(ReservationFormDTO reservationFormDTO, long storeId){
        Store store = storeRepository.findById(storeId).get();
        LocalDateTime timeToClose = reservationFormDTO.generateTimeToClose();
        List<Reservation> reservations = reservationFormDTO.generateReservations(store);
        reservations.forEach(reservation -> reservation.regist());
        store.activate(timeToClose);
        reservationRepository.saveAll(reservations);
    }

    public List<Reservation> getReservationsByCondition(String condition, Store store) {
        ReservationSelector selector = (ReservationSelector)beanFactory.getBean(condition + SELECTOR_POST_FIX);
        return selector.select(store);
    }
}