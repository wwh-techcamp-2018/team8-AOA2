package com.aoa.springwebservice.domain;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class StoreTest {

    @Test
    public void store_addMenu(){
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

        Menu menu = new Menu("test", 1, "가게관점createMenu", "/");

        store.addMenu(menu);

        assertThat(store.hasMenu(menu)).isTrue();
    }
}
