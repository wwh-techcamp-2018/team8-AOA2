package com.aoa.springwebservice.web;

import com.aoa.springwebservice.RestResponse;
import com.aoa.springwebservice.domain.Reservation;
import com.aoa.springwebservice.domain.ReservationRepository;
import com.aoa.springwebservice.domain.StoreRepository;
import com.aoa.springwebservice.dto.ReservationFormDTO;
import lombok.extern.slf4j.Slf4j;
import com.aoa.springwebservice.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiReservationController {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @PostMapping("/stores/{storeId}/reservations")
    public RestResponse<RestResponse.RedirectData> create(@PathVariable long storeId, @RequestBody ReservationFormDTO reservationDTO) {
        reservationService.createReservation(reservationDTO, storeId);
        return RestResponse.ofRedirectResponse("/result/success", "OK");
    }

    @GetMapping("/stores/{storeId}/reservations")
    public List<Reservation> list(@PathVariable long storeId) {
        return reservationRepository.findAllByStore( storeRepository.findById(storeId).get());
    }

}
