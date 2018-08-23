package com.aoa.springwebservice.web;

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

    @PostMapping("/stores/{storeId}/reservations")
    public void create(@PathVariable long storeId, @RequestBody ReservationFormDTO reservationDTO) {
        reservationService.createReservation(reservationDTO, storeId);
    }
    @PostMapping("/stores/{storeId}/reservations/temp")
    public String create2(@PathVariable long storeId, @RequestBody ReservationFormDTO reservationDTO) {
        log.debug("ReservationDTO {}", reservationDTO);
        return "/result/success";
    }

    @GetMapping("/stores/{storeId}/reservations")
    public List<ReservationFormDTO> list() {
        return null;
    }

}
