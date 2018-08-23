package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.Reservation;
import com.aoa.springwebservice.domain.ReservationRepository;
import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.domain.StoreRepository;
import com.aoa.springwebservice.dto.OrderFormDTO;
import com.aoa.springwebservice.dto.OrderItemDTO;
import com.aoa.springwebservice.dto.ReservationFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    public List<Reservation> getTodayReservations(long storeId) {
        //todo : store의 오픈, 마감시간과 Reservation의 날짜 확인 필요
        return reservationRepository.findAllByStoreId(storeId);
    }

    public void servicemain(OrderFormDTO orderFormDTO, long storeId) {
        List<Reservation> reservations = getTodayReservations(storeId);
        orderFormDTO.getOrderItemDTOs().stream().forEach(e -> {
            reservations.stream().forEach(reservation -> {
                if(e.getMenuId() == reservation.getMenu().getId()){
                    if(reservation.getAvailableCount() - e.getItemCount() <0){
                        throw new RuntimeException("Can't Buy!");
                    }
                    reservation.orderMenu(e.getItemCount());
                }
            });
        });
    }

    public Reservation updateReservationCurrentAmount(OrderItemDTO orderItemDTO, Reservation reservation) {
        if(orderItemDTO.getMenuId() == reservation.getMenu().getId()){
            if(reservation.getAvailableCount() - orderItemDTO.getItemCount() <0){
                throw new RuntimeException("Can't Buy!");
            }
            reservation.orderMenu(orderItemDTO.getItemCount());
        }
        return reservation;
    }
}
