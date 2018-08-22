package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.Reservation;
import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.domain.StoreRepository;
import com.aoa.springwebservice.dto.ReservationFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiReservationController {

    @Autowired
    private StoreRepository storeRepository;

    @PostMapping("/stores/{storeId}/reservations")
    public void create(@PathVariable long storeId, ReservationFormDTO reservationDTO) {
        LocalDateTime timeToClose = reservationDTO.generateTimeToClose();
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("없는 가게"));
        List<Reservation> reservations = reservationDTO.generateReservations(store);
        store.updateReservation(reservations, timeToClose);
    }

    @GetMapping("/stores/{storeId}/reservations")
    public List<ReservationFormDTO> list() {
        return null;
    }

}
