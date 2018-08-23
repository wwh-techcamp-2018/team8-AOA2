package com.aoa.springwebservice.domain.repository;

import com.aoa.springwebservice.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private StoreRepository storeRepository;

    private Store defaultStore;
    private Menu defaultMenu;

    private List<Reservation> targetReservations;


    @Before
    public void setUp() {
        prepareDefaultStore();
        prepareDefaultMenus();
    }

    @Test
    public void list_current_reservations_있을때() {
        setUp_current_있을때();
    }

    private void setUp_current_있을때(){
        targetReservations = Arrays.asList(
                Reservation.builder()
                        .build(),
                Reservation.builder()
                        .build(),
                Reservation.builder()
                        .build()
        );
        reservationRepository.saveAll(targetReservations);
    }

    @Test
    public void list_current_reservations_없을때() {
        setUp_current_없을때();
    }

    private void setUp_current_없을때() {
        targetReservations = Arrays.asList(
                Reservation.builder()
                        .build(),
                Reservation.builder()
                        .build(),
                Reservation.builder()
                        .build()
        );
        reservationRepository.saveAll(targetReservations);
    }

    @Test
    public void list_last_reservations_하루전() {
        setUp_last_case_하루전();
    }

    private void setUp_last_case_하루전(){
        targetReservations = Arrays.asList(
                Reservation.builder()
                        .build(),
                Reservation.builder()
                        .build(),
                Reservation.builder()
                        .build()
        );
        reservationRepository.saveAll(targetReservations);
    }

    @Test
    public void list_last_reservations_여러날전() {
        setUp_last_case_여러날전();
    }

    private void setUp_last_case_여러날전(){
        targetReservations = Arrays.asList(
                Reservation.builder()
                        .build(),
                Reservation.builder()
                        .build(),
                Reservation.builder()
                        .build()
        );
        reservationRepository.saveAll(targetReservations);
    }

    private void prepareDefaultStore() {
        defaultStore = Store.builder()
                .description("DESC")
                .imgURL("img")
                .ownerName("주인")
                .phoneNumber("1234512345")
                .postCode("12345")
                .serviceDescription("create menu 가게관점")
                .storeName("storeName")
                .address("ADDRESS")
                .build();
        storeRepository.save(defaultStore);
    }

    private void prepareDefaultMenus() {
        defaultMenu = Menu.builder().store(defaultStore).name("test1").description("test1").price(1).imageUrl("/path").build();
        defaultMenu = Menu.builder().store(defaultStore).name("test2").description("test2").price(2).imageUrl("/path").build();
        defaultStore.addMenu(defaultMenu);
        storeRepository.save(defaultStore);
    }
}
