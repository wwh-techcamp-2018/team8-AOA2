package com.aoa.springwebservice.domain;

import com.aoa.springwebservice.dto.ReservationDTO;
import com.aoa.springwebservice.dto.ReservationFormDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@Slf4j
public class ReservationTest {

    private SoftAssertions softly;
    private Store store;
    private List<ReservationDTO> reservationDTOs;
    private ReservationFormDTO reservationFormDTO;

    @Before
    public void setUp(){
        softly = new SoftAssertions();
        store = Store.builder()
                .description("DESC")
                .imgURL("img")
                .ownerName("주인")
                .phoneNumber("1234512345")
                .postCode("12345")
                .serviceDescription("create menu 가게관점")
                .storeName("storeName")
                .address("ADDRESS")
                .build();
        store.addMenu(Menu.builder().store(store).name("test1").description("test1").price(1).id(1L).build());
        store.addMenu(Menu.builder().store(store).name("test2").description("test2").price(2).id(2L).build());

        reservationDTOs = Arrays.asList(
                ReservationDTO.builder().maxCount(3).personalMaxCount(3).menuId(1L).build()
                , ReservationDTO.builder().maxCount(3).personalMaxCount(3).menuId(2L).build());

        reservationFormDTO = ReservationFormDTO.builder()
                .hourToClose(11)
                .minuteToClose(0)
                .reservationDTOs(reservationDTOs)
                .build();
    }

    @Test
    public void test_ReservationFormDTO_generateReservations() {
        List<Reservation> reservations = reservationFormDTO.generateReservations(store);
        softly.assertThat(reservations
                .stream()
                .filter(reservation -> reservation.getStore() == null))
                .as("Store 다 넣어줬는지")
                .isEmpty();
        softly.assertThat(reservations
                .stream()
                .filter(reservation -> !store.hasMenu(reservation.getMenu())))
                .as("Store에 있는 Menu 중에 다 넣어줬는지")
                .isEmpty();
        softly.assertAll();
    }
}
