package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.Reservation;
import com.aoa.springwebservice.domain.ReservationRepository;
import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.domain.StoreRepository;
import com.aoa.springwebservice.dto.ReservationDTO;
import com.aoa.springwebservice.dto.ReservationFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private StoreRepository storeRepository;


    @Transactional
    public void createReservation(ReservationFormDTO reservationFormDTO, long storeId){
        Store store = storeRepository.findById(storeId).get();
        LocalDateTime timeToClose = reservationFormDTO.generateTimeToClose();
        List<Reservation> reservations = reservationFormDTO.generateReservations(store);
        reservations.forEach(reservation -> reservation.regist());
        store.activate(timeToClose);
        reservationRepository.saveAll(reservations);
    }

    public List<ReservationDTO> getReservations(String type) {
        return null;
    }

}
