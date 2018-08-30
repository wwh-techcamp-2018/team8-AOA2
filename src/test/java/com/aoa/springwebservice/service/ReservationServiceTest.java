package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.*;
import com.aoa.springwebservice.dto.ReservationDTO;
import com.aoa.springwebservice.dto.ReservationFormDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ReservationServiceTest {

//    private Store store;
//    private Menu menu1;
//    private Menu menu2;
//    private Menu menu3;
//    private Menu menu4;
//    private Menu menu5;
//
//    @Autowired
//    private ReservationRepository reservationRepository;
//
//    @Autowired
//    private StoreRepository storeRepository;
//
//    @Autowired
//    private ReservationService reservationService;
//
//    @Before
//    public void setUp() {
//        store = Store.builder()
//                .description("DESC")
//                .imgURL("img")
//                .ownerName("주인")
//                .phoneNumber("1234512345")
//                .postCode("12345")
//                .serviceDescription("create menu 가게관점")
//                .storeName("storeName")
//                .address("ADDRESS")
//                .addressDetail("address")
//                .timeToClose(LocalDateTime.now())
//                .build();
//        log.debug("before store : {}", store);
//        store = storeRepository.save(store);
//        log.debug("after store : {}", store);
//
//        menu1 = Menu.builder().store(store).name("test1").description("test1").price(1).id(1L).build();
//        menu2 = Menu.builder().store(store).name("test2").description("test2").price(2).id(2L).build();
//        menu3 = Menu.builder().store(store).name("test3").description("test3").price(3).id(3L).build();
//        menu4 = Menu.builder().store(store).name("test4").description("test4").price(4).id(4L).build();
//        menu5 = Menu.builder().store(store).name("test5").description("test5").price(5).id(5L).build();
//
//        store.addMenu(menu1);
//        store.addMenu(menu2);
//        store.addMenu(menu3);
//        store.addMenu(menu4);
//        store.addMenu(menu5);
//        log.debug("after add store : {}", store);
//
//        store.deactivate();
//        storeRepository.save(store);
//    }
//
//    @Test
//    public void create_new_reservation() {
//        // When
//        long storeId = store.getId();
//        List<ReservationDTO> reservationDTOs = Arrays.asList(
//                ReservationDTO.builder()
//                        .maxCount(3)
//                        .personalMaxCount(3)
//                        .menuId(1L)
//                        .build(),
//                ReservationDTO.builder()
//                        .maxCount(3)
//                        .personalMaxCount(3)
//                        .menuId(2L)
//                        .build());
//        ReservationFormDTO reservationFormDTO = ReservationFormDTO.builder()
//                .hourToClose(12)
//                .minuteToClose(0)
//                .reservationDTOs(reservationDTOs)
//                .build();
//
//        reservationService.createReservation(reservationFormDTO, store);
//
//        assertThat(reservationRepository.findAllByStoreId(storeId).size()).isEqualTo(reservationDTOs.size());
//    }

    @Test
    public void pass(){

    }
}
