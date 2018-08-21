package com.aoa.springwebservice.domain;

import com.aoa.springwebservice.domain.support.MenuDTO;
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

        MenuDTO menuDTO = new MenuDTO("test", 1, "가게관점createMenu", "/");
        Menu menu = menuDTO.toDomain(store);

        assertThat(store.addMenu(menu)).isTrue();
        assertThat(store.hasMenu(menu)).isTrue();
    }
}
