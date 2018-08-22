package com.aoa.springwebservice.Service;

import com.aoa.springwebservice.domain.*;
import com.aoa.springwebservice.dto.ReservationDTO;
import com.aoa.springwebservice.dto.ReservationFormDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationServiceTest {

    private Store store;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Before
    public void setUp() {
        store = Store.builder()
                .description("DESC")
                .imgURL("img")
                .ownerName("주인")
                .phoneNumber("1234512345")
                .postCode("12345")
                .serviceDescription("create menu 가게관점")
                .storeName("storeName")
                .address("ADDRESS")
                .addressDetail("address")
                .timeToClose(LocalDateTime.now())
                .build();
        store = storeRepository.save(store);

        store.addMenu(Menu.builder().store(store).name("test1").description("test1").price(1).id(1L).build());
        store.addMenu(Menu.builder().store(store).name("test2").description("test2").price(2).id(2L).build());
        store.addMenu(Menu.builder().store(store).name("test3").description("test3").price(3).id(3L).build());
        store.addMenu(Menu.builder().store(store).name("test4").description("test4").price(4).id(4L).build());
        store.addMenu(Menu.builder().store(store).name("test5").description("test5").price(5).id(5L).build());
        store = storeRepository.save(store);
    }

    @Test
    public void create_new_reservation() {
        // When
        List<ReservationDTO> reservationDTOs = Arrays.asList(
                ReservationDTO.builder()
                        .maxCount(3)
                        .personalMaxCount(3)
                        .menuId(1L)
                        .build(),
                ReservationDTO.builder()
                        .maxCount(3)
                        .personalMaxCount(3)
                        .menuId(2L)
                        .build());
        ReservationFormDTO reservationFormDTO = ReservationFormDTO.builder()
                .hourToClose(12)
                .minuteToClose(0)
                .reservationDTOs(reservationDTOs)
                .build();
        LocalDateTime timeToClose = reservationFormDTO.generateTimeToClose();
        store.deactivate();
//
//        List<Reservation> reservations = reservationFormDTO.generateReservations(store);
//        reservations.forEach(reservation -> reservation.regist());
//        store.activate(reservations, timeToClose);

        assertThat(reservationRepository.findAllByStore(store).size()).isEqualTo(reservationDTOs.size());
    }

}
