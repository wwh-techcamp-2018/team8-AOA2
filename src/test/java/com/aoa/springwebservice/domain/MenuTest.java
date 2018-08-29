package com.aoa.springwebservice.domain;

import com.aoa.springwebservice.dto.MenuDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class MenuTest {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    StoreRepository storeRepository;

    @Test
    public void testCreate_메뉴_가게_매핑_메뉴관점(){
        Store store = Store.builder()
                .description("DESC")
                .imgURL("img")
                .ownerName("주인")
                .phoneNumber("1234512345")
                .postCode("12345")
                .serviceDescription("serviceDESC")
                .storeName("storeName")
                .address("ADDRESS")
                .build();
        store = storeRepository.save(store);
        Menu menu = new Menu("NAME", 1000, "DESC", "/img");
        //store.addMenu(); // cascade
        menu.setStore(store);
        menu = menuRepository.save(menu);
        //assertThat(store.getMenus....(menu.getId()))
        assertThat(menu.getStore()).isEqualTo(store);
        assertThat(menu.hasSameStore(store)).isTrue();
    }

    @Test
    public void testCreate_메뉴_가게_매핑_가게관점(){
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
        store = storeRepository.save(store);
        MenuDTO menuDTO = new MenuDTO("test", 1, "가게관점createMenu", "/");
        Menu menu = menuDTO.toDomain(store);
        log.debug("store before add menu : {}", store);
        store.addMenu(menu);
        log.debug("store after add menu : {}", store);
        store = storeRepository.save(store);
        log.debug("store after update : {}", store);
        assertThat(store.hasMenu(menu)).isTrue();
    }

    @Test
    public void testCreateMenuOutputDTO(){
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
    }

}
