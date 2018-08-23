package com.aoa.springwebservice.domain.repository;

import com.aoa.springwebservice.domain.*;
import com.aoa.springwebservice.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private static final LocalDate PAST_DATE = LocalDate.ofEpochDay(0L);


    @Before
    public void setUp() {
        prepareDefaultStore();
        prepareDefaultMenus();
        preparePastReservations();
    }

    @Test
    public void list_current_reservations_있을때() {
        int expected = 3;
        setUp_current(expected);
        List<Reservation> actualReservations = reservationRepository.findAllByStoreAndOpenDate(defaultStore, LocalDate.now());
        assertThat(actualReservations).isNotEmpty();
        assertThat(actualReservations.size()).isEqualTo(expected);
    }

    @Test
    public void list_current_reservations_없을때() {
        int expected = 0;
        setUp_current(expected);
        List<Reservation> actualReservations = reservationRepository.findAllByStoreAndOpenDate(defaultStore, LocalDate.now());
        assertThat(actualReservations).isEmpty();
    }

    private void setUp_current(int expected) {
        for (int i = 0; i < expected; i++) {
            targetReservations.add(generateTestReservation(LocalDate.now()));
        }
        reservationRepository.saveAll(targetReservations);
    }


    @Test
    public void list_last_reservations_하루전() {
        int expected = 3;
        long termOfPastDays = 1;
        setUp_last_case(expected, termOfPastDays);


        LocalDate lastDate = reservationRepository
                .findFirstByStoreAndOpenDateBeforeOrderByOpenDateDesc(defaultStore, LocalDate.now())
                .get()
                .getOpenDate();

        log.debug("lastDate : {}", lastDate);

        List<Reservation> actualReservations = reservationRepository.findAllByStoreAndOpenDate(defaultStore, lastDate);

        assertThat(actualReservations).isNotEmpty();
        assertThat(actualReservations.size()).isEqualTo(expected);
    }

    @Test
    public void list_last_reservations_여러날전() {
        int expected = 5;
        long termOfPastDays = 10;
        setUp_last_case(expected, termOfPastDays);

        LocalDate lastDate = reservationRepository
                .findFirstByStoreAndOpenDateBeforeOrderByOpenDateDesc(defaultStore, LocalDate.now())
                .get()
                .getOpenDate();

        log.debug("lastDate : {}", lastDate);

        List<Reservation> actualReservations = reservationRepository.findAllByStoreAndOpenDate(defaultStore, lastDate);

        assertThat(actualReservations).isNotEmpty();
        assertThat(actualReservations.size()).isEqualTo(expected);
    }

    private void setUp_last_case(int expected, long termOfPastDays) {
        for (int i = 0; i < expected; i++) {
            targetReservations.add(generateTestReservation(LocalDate.now().minusDays(termOfPastDays)));
        }
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
        defaultMenu = Menu.builder()
                .store(defaultStore)
                .name("test1")
                .description("test1")
                .price(1)
                .imageUrl("/path")
                .build();
        defaultMenu = Menu.builder().store(defaultStore).name("test2").description("test2").price(2).imageUrl("/path").build();
        defaultStore.addMenu(defaultMenu);
        defaultStore = storeRepository.save(defaultStore);
        defaultMenu = defaultStore.getMenus().get(0);
    }

    private void preparePastReservations() {
        targetReservations = new ArrayList<Reservation>();
        targetReservations.addAll(
                Arrays.asList(
                generateTestReservation(PAST_DATE.plusYears(4L)),
                generateTestReservation(PAST_DATE.plusYears(3L)),
                generateTestReservation(PAST_DATE.plusYears(2L)),
                generateTestReservation(PAST_DATE.plusYears(1L)),
                generateTestReservation(PAST_DATE)
        ));
        reservationRepository.saveAll(targetReservations);
    }

    private Reservation generateTestReservation(LocalDate openDate){
        log.debug("defaultMaxCount : {}", defaultMaxCount());
        return Reservation.builder()
                .maxCount(defaultMaxCount())
                .store(defaultStore)
                .menu(defaultMenu)
                .openDate(openDate)
                .build();
    }

    private MaxCount defaultMaxCount(){
        return new MaxCount(2, 1);
    }
}
