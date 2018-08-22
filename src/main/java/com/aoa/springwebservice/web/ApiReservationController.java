package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.StoreRepository;
import com.aoa.springwebservice.dto.ReservationFormDTO;
import com.aoa.springwebservice.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiReservationController {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/stores/{storeId}/reservations")
    public void create(@PathVariable long storeId, @RequestBody ReservationFormDTO reservationDTO) {
        reservationService.createReservation(reservationDTO, storeId);
    }

    @GetMapping("/stores/{storeId}/reservations")
    public List<ReservationFormDTO> list() {
        return null;
    }

}
