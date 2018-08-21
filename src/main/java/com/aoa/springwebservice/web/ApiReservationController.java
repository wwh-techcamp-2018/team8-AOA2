package com.aoa.springwebservice.web;

import com.aoa.springwebservice.dto.ReservationFormDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiReservationController {

    @PostMapping("/stores/{storeId}/reservations")
    public ReservationFormDTO create(ReservationFormDTO reservationDTO){
        return null;
    }
    @GetMapping("/stores/{storeId}/reservations")
    public List<ReservationFormDTO> list(){
        return null;
    }
    //todo delete, update (제품수량, 1인당 구매제한)
    //todo 예약 마감시간 update, read -- StoreService 위임
}
