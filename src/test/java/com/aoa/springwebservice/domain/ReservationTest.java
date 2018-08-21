package com.aoa.springwebservice.domain;

import com.aoa.springwebservice.dto.ReservatioDTO;
import com.aoa.springwebservice.dto.ReservationFormDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@Slf4j
public class ReservationTest {

    @Test
    public void test_ReservationFormDTO_generateReservations() {
        Store store = Store.builder()
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

        List<ReservatioDTO> reservatioDTOS = Arrays.asList(
                ReservatioDTO.builder().maxCount(3).personalMaxCount(3).menuId(1L).build()
                , ReservatioDTO.builder().maxCount(3).personalMaxCount(3).menuId(2L).build());

        ReservationFormDTO reservationFormDTO = ReservationFormDTO.builder()
                .hourToClose(11)
                .minuteToClose(0)
                .reservatioDTOs(reservatioDTOS)
                .build();

        List<Reservation> reservations = reservationFormDTO.generateReservations(store);

        for(Reservation reservation : reservations){
            log.debug("reservation {}", reservation);
            assertThat(store.getMenus()).contains(reservation.getMenu());
        }
    }
}
