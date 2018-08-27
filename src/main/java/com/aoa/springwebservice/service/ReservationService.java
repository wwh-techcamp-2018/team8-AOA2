package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.Reservation;
import com.aoa.springwebservice.domain.ReservationRepository;
import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.domain.StoreRepository;
import com.aoa.springwebservice.dto.OrderFormDTO;
import com.aoa.springwebservice.dto.OrderItemDTO;
import com.aoa.springwebservice.dto.ReservationDTO;
import com.aoa.springwebservice.dto.ReservationFormDTO;
import com.aoa.springwebservice.service.support.ReservationSelector;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public Iterable<Reservation> createReservation(ReservationFormDTO reservationFormDTO, long storeId){
        Store store = storeRepository.findById(storeId).get();
        LocalDateTime timeToClose = reservationFormDTO.generateTimeToClose();
        List<Reservation> reservations = reservationFormDTO.generateReservations(store);
        reservations.forEach(reservation -> reservation.regist());
        store.activate(timeToClose);
        return reservationRepository.saveAll(reservations);
    }

    public Map<Long, Reservation> getTodayReservations(long storeId) {
        //todo : store의 오픈, 마감시간과 Reservation의 날짜 확인 필요
        return convertReservationListToMap(reservationRepository.findAllByStoreId(storeId));
    }

    private Map<Long, Reservation> convertReservationListToMap(List<Reservation> reservations) {
        return reservations.stream().collect(Collectors.toMap(Reservation::getId, reservation -> reservation));
    }

    public List<Reservation> getReservationsByCondition(String condition, Store store) {
        ReservationSelector selector = (ReservationSelector)beanFactory.getBean(condition + SELECTOR_POST_FIX);
        return selector.select(store);
    }

    public LocalDate getLastDay(Store store) {
        //todo : pageController에서 전날 데이터 없을 때 exception 처리해야된다.
        return reservationRepository
                .findFirstByStoreAndOpenDateBeforeOrderByOpenDateDesc(store, LocalDate.now())
                .get()
                .getOpenDate();
    }
}
