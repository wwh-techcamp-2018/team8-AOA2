package com.aoa.springwebservice.domain;

import com.aoa.springwebservice.dto.MenuDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
@Slf4j
public class StoreTest {

    private Store store;
    private SoftAssertions softly = new SoftAssertions();

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
    public void store_update() {
        Store newInfo = Store.builder()
                .description("new description")
                .imgURL("newimg")
                .phoneNumber("01012341234")
                .postCode("43210")
                .serviceDescription("update create menu 가게관점")
                .address("new ADDRESS")
                .build();
        store.updateStore(newInfo);
        log.debug("store : {}", store);
        log.debug("newInfo : {}", newInfo);

        softly.assertThat(store.getDescription()).as("메뉴변경").isEqualTo(newInfo.getDescription());
        softly.assertThat(store.getImgURL()).isEqualTo(newInfo.getImgURL());
        softly.assertThat(store.getPhoneNumber()).isEqualTo(newInfo.getPhoneNumber());
        softly.assertThat(store.getPostCode()).isEqualTo(newInfo.getPostCode());
        softly.assertThat(store.getServiceDescription()).isEqualTo(newInfo.getServiceDescription());
        softly.assertThat(store.getAddress()).isEqualTo(newInfo.getAddress());
        softly.assertAll();
    }

    @Test
    public void store_deactivate(){
        //When
        List<Reservation> reservations = null;
        LocalDateTime timeToClose = LocalDateTime.now();
        store.activate(timeToClose);
        store.deactivate();
        assertThat(store.isOpen()).isFalse();
    }
}
