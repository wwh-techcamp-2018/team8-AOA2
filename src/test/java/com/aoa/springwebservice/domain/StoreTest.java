package com.aoa.springwebservice.domain;

import com.aoa.springwebservice.domain.support.MenuDTO;
import com.aoa.springwebservice.dto.ReservatioDTO;
import com.aoa.springwebservice.dto.ReservationFormDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

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
    public void createReservation(){
        List<ReservatioDTO> reservatioDTOS = Arrays.asList(
                ReservatioDTO.builder().maxCount(3).personalMaxCount(3).menuId(1L).build()
                , ReservatioDTO.builder().maxCount(3).personalMaxCount(3).menuId(2L).build());

        ReservationFormDTO reservationFormDTO = ReservationFormDTO.builder()
                .hourToClose(11)
                .minuteToClose(0)
                .reservatioDTOs(reservatioDTOS)
                .build();

        List<Reservation> reservations = reservationFormDTO.generateReservations(store);
        LocalDateTime timeToClose = reservationFormDTO.generateTimeToClose();

        store.updateReservation(reservations, timeToClose);

        /*
            // param) reservations 날짜 세팅된 애들


            updateReservation{
                0) 이 스토어가 새로운 예약을 등록할 수 있는 상황인지 조건 확인 (Status == Closed)
                1) 시간 설정 (timeToClose)
                2) 소속 메뉴들 다 안사용 바꾸기
                3) reservations parameter 에 있는 reservation 의 메뉴들 수정
                    * 사용으로 바꾸기
                    * 예약 가능 갯수 바꾸기
                    * 최대 예약 갯수 바꾸기
                4) store 에 소속된 reservations(예약정보)에 파라미터 add // 모두 삭제 후 insert? status확인? 등 로직?
            }

            * Cascade 적용
            Store updatedStore = storeRepository.save(store);

         */

    }
}
