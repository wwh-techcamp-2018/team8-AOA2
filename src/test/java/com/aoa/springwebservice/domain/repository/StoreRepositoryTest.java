package com.aoa.springwebservice.domain.repository;

import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.domain.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    private Store defaultStore;

    @Before
    public void setUp(){
        defaultStore = Store.builder()
                .description("DESC")
                .imgURL("img")
                .ownerName("주인")
                .phoneNumber("1234512345")
                .postCode("12345")
                .serviceDescription("create menu 가게관점")
                .storeName("storeName")
                .address("ADDRESS")
                .timeToClose(LocalDateTime.now().plusMinutes(40))
                .build();
        defaultStore = storeRepository.save(defaultStore);
    }

    @Test
    public void updateStatusTest_false(){
        Store store = storeRepository.findById(defaultStore.getId()).get();
        log.debug("store : {}", store);
        log.debug("isOpen : {}", defaultStore.isOpen());
    }

    @Test
    public void updateStatusTest_withTimeToClose(){
        Store store = storeRepository.findById(defaultStore.getId()).get();

        log.debug("before store : {}", store);
        log.debug("before isOpen : {}", defaultStore.isOpen());
        Store afterStore = storeRepository.findById(defaultStore.getId()).get();
        store = storeRepository.save(afterStore);
        log.debug("after store : {}", store);
        log.debug("after isOpen : {}", defaultStore.isOpen());
    }

    @Test
    public void timeToClose(){
        Store store = storeRepository.findById(defaultStore.getId()).get();
        LocalDateTime targetDateTime = store.getTimeToClose();
        log.debug("targetDateTime : {}", targetDateTime);
        log.debug("now DateTime : {}", LocalDateTime.now());
        log.debug("isOpen : {}", store.isOpen());
        assertThat(store.isOpen()).isTrue();
    }
}
