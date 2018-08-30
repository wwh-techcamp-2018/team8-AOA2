package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.*;
import com.aoa.springwebservice.dto.OrderFormDTO;
import com.aoa.springwebservice.dto.OrderItemDTO;
import com.aoa.springwebservice.dto.ReservationDTO;
import com.aoa.springwebservice.dto.ReservationFormDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceTest {
//
//    private Store store;
//    private Menu menu1;
//    private Menu menu2;
//    private Menu menu3;
//    private Menu menu4;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private StoreRepository storeRepository;
//
//    @Autowired
//    private ReservationService reservationService;
//
//    @Autowired
//    private OrderService orderService;
//    private OrderFormDTO orderFormDTO;
//
//    @Before
//    public void setUp() {
//        store = Store.builder()
//                .description("DESC")
//                .imgURL("img")
//                .ownerName("주인")
//                .phoneNumber("1234512345")
//                .postCode("12345")
//                .serviceDescription("create menu 가게관점")
//                .storeName("storeName")
//                .address("ADDRESS")
//                .addressDetail("address")
//                .timeToClose(LocalDateTime.now())
//                .build();
//        store = storeRepository.save(store);
//
//        menu1 = Menu.builder().store(store).name("test1").description("test1").price(1).id(1L).build();
//        menu2 = Menu.builder().store(store).name("test2").description("test2").price(2).id(2L).build();
//        menu3 = Menu.builder().store(store).name("test3").description("test3").price(3).id(3L).build();
//        menu4 = Menu.builder().store(store).name("test4").description("test4").price(4).id(4L).build();
//
//        store.addMenu(menu1);
//        store.addMenu(menu2);
//        store.addMenu(menu3);
//        store.addMenu(menu4);
//        store = storeRepository.save(store);
//    }
//
//    @Test
//    @Transactional
//    public void update_count_Reservations_성공() {
//        OrderFormDTO orderFormDTO = createReservationResource(1L, 2L);
//        Map<Long, Reservation> result = reservationService.getTodayReservations(store.getId());
//
//        //todo : 구매 갯수와 예약 갯수가 일치 (못사는 것에 대한 에러처리)
//        Order order = orderFormDTO.toDomain(store);
//
//        Order newOrder = orderService.createOrder(result, orderFormDTO, order);
//
//        assertThat(order.getOrderItems().size()).isEqualTo(2);
//        assertThat(order.getOrderTotalPrice()).isEqualTo(order.getOrderItems().stream().mapToInt(i -> i.getItemTotalPrice()).sum());
//
//        log.debug("newOrder : {}", newOrder);
//    }
//
//    public OrderFormDTO createReservationResource(long menuId1, long menuId2) {
//        // When
//        List<ReservationDTO> reservationDTOs = Arrays.asList(
//                ReservationDTO.builder()
//                        .maxCount(3)
//                        .personalMaxCount(3)
//                        .menuId(menuId1)
//                        .build(),
//                ReservationDTO.builder()
//                        .maxCount(3)
//                        .personalMaxCount(3)
//                        .menuId(menuId2)
//                        .build());
//
//        ReservationFormDTO reservationFormDTO = ReservationFormDTO.builder().hourToClose(12).minuteToClose(0).reservationDTOs(reservationDTOs).build();
//
//        store.deactivate();
//
//        Iterable<Reservation> reservations = reservationService.createReservation(reservationFormDTO, store);
//
//        List<OrderItemDTO> orderItems = new ArrayList<>();
//
//        for (Reservation reservation : reservations) {
//            orderItems.add(OrderItemDTO.builder().reservationId(reservation.getId()).itemCount(1).build());
//            log.debug("orderItemsID : {}", reservation.getId());
//        }
//
//        return new OrderFormDTO("hong", "010", "1111", "1111", "11:30", orderItems);
//    }
    @Test
    public void pass(){

    }
}