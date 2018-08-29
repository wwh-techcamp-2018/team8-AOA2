package com.aoa.springwebservice.web;

import com.aoa.springwebservice.RestResponse;
import com.aoa.springwebservice.domain.Reservation;
import com.aoa.springwebservice.domain.ReservationRepository;
import com.aoa.springwebservice.domain.StoreRepository;
import com.aoa.springwebservice.dto.ReservationFormDTO;
import com.aoa.springwebservice.service.StoreService;
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
    private StoreService storeService;

    @Autowired
    private ReservationService reservationService;


    @PostMapping("/stores/{storeId}/reservations")
    public RestResponse<RestResponse.RedirectData> create(@PathVariable long storeId, @RequestBody ReservationFormDTO reservationDTO) {
        reservationService.createReservation(reservationDTO, storeId);
        return RestResponse.ofRedirectResponse("/result/success", "OK");
    }

    @GetMapping(value = "/stores/{storeId}/reservations", params = "conditions")
    public List<Reservation> list(@RequestParam String conditions, @PathVariable long storeId) {
        return  reservationService.getReservationsByCondition(conditions, storeService.getStoreById(storeId));
    }

}
