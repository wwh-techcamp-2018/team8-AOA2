package com.aoa.springwebservice.domain;

import com.aoa.springwebservice.dto.MenuDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
@Slf4j
public class StoreTest {

    private Store store;

    @Before
    public void setUp(){
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
        store.addMenu(Menu.builder().store(store).name("test3").description("test3").price(3).id(3L).build());
        store.addMenu(Menu.builder().store(store).name("test4").description("test4").price(4).id(4L).build());
        store.addMenu(Menu.builder().store(store).name("test5").description("test5").price(5).id(5L).build());
    }

    @Test
    public void store_addMenu(){
        MenuDTO menuDTO = new MenuDTO("test", 1, "가게관점createMenu", "/");
        Menu menu = menuDTO.toDomain(store);

        assertThat(store.addMenu(menu)).isTrue();
        assertThat(store.hasMenu(menu)).isTrue();
    }

    @Test
    public void store_deactivate(){
        //When
        List<Reservation> reservations = null;
        LocalDateTime timeToClose = null;
        store.activate(timeToClose);

        store.deactivate();
        assertThat(store.isOpen()).isFalse();
    }
}
