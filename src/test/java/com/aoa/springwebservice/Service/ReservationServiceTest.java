package com.aoa.springwebservice.Service;

import com.aoa.springwebservice.domain.*;
import com.aoa.springwebservice.dto.OrderFormDTO;
import com.aoa.springwebservice.dto.OrderItemDTO;
import com.aoa.springwebservice.dto.ReservationDTO;
import com.aoa.springwebservice.dto.ReservationFormDTO;
import com.aoa.springwebservice.service.OrderService;
import com.aoa.springwebservice.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ReservationServiceTest {

    private Store store;
    private Menu menu1;
    private Menu menu2;
    private Menu menu3;
    private Menu menu4;
    private Menu menu5;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ReservationService reservationService;


    @Autowired
    private OrderService orderService;

    @Before
    public void setUp() {
        store = Store.builder()
                .description("DESC")
                .imgURL("img")
                .ownerName("주인")
                .phoneNumber("1234512345")
                .postCode("12345")
                .serviceDescription("create menu 가게관점")
                .storeName("storeName")
                .address("ADDRESS")
                .addressDetail("address")
                .timeToClose(LocalDateTime.now())
                .build();
        store = storeRepository.save(store);

        menu1 = Menu.builder().store(store).name("test1").description("test1").price(1).id(1L).build();
        menu2 = Menu.builder().store(store).name("test2").description("test2").price(2).id(2L).build();
        menu3 = Menu.builder().store(store).name("test3").description("test3").price(3).id(3L).build();
        menu4 = Menu.builder().store(store).name("test4").description("test4").price(4).id(4L).build();
        menu5 = Menu.builder().store(store).name("test5").description("test5").price(5).id(5L).build();

        store.addMenu(menu1);
        store.addMenu(menu2);
        store.addMenu(menu3);
        store.addMenu(menu4);
        store.addMenu(menu5);
        store = storeRepository.save(store);
    }

    @Test
    public void create_new_reservation() {
        // When
        List<ReservationDTO> reservationDTOs = Arrays.asList(
                ReservationDTO.builder()
                        .maxCount(3)
                        .personalMaxCount(3)
                        .menuId(1L)
                        .build(),
                ReservationDTO.builder()
                        .maxCount(3)
                        .personalMaxCount(3)
                        .menuId(2L)
                        .build());
        ReservationFormDTO reservationFormDTO = ReservationFormDTO.builder()
                .hourToClose(12)
                .minuteToClose(0)
                .reservationDTOs(reservationDTOs)
                .build();
        store.deactivate();

        log.debug("test start");

        reservationService.createReservation(reservationFormDTO, store.getId());

        assertThat(reservationRepository.findAllByStore(store).size()).isEqualTo(reservationDTOs.size());
    }

//    @Test
//    public void get_today_reservations() {
//        //todo : store의 오픈, 마감시간과 Reservation의 날짜 확인
//        MaxCount maxCount = new MaxCount(10, 10);
//        Reservation.builder().maxCount(maxCount).menu(menu1).store(store).build();
//        Reservation.builder().maxCount(maxCount).menu(menu2).store(store).build();
//        Reservation.builder().maxCount(maxCount).menu(menu3).store(store).build();
//        Reservation.builder().maxCount(maxCount).menu(menu4).store(store).build();
//        Reservation.builder().maxCount(maxCount).menu(menu5).store(store).build();
//
//
//        assertThat(reservationService.getTodayReservations(store.getId()).size()).isEqualTo(5);
//    }


    @Test
    public void update_count_Reservations() {
        OrderFormDTO orderFormDTO = init_data();
        //todo : map으로 변경
        List<Reservation> reservations = reservationService.getTodayReservations(store.getId());

        log.debug("reservation : {}", reservations);

        //orderItem list에서 돌면서 Reservations에 같은 menu_id 있으면 update
        //todo : 구매 갯수와 예약 갯수가 일치.

        Map<Long, Reservation> result =
                reservations.stream().collect(Collectors.toMap(Reservation::getId, reservation -> reservation));
        Order order = new Order(null, store, LocalDateTime.now());
        orderFormDTO.getOrderItemDTOs().stream().forEach( orderItemDTO -> {

            reservations.stream().forEach(reservation -> {
                if(orderItemDTO.getMenuId() == reservation.getMenu().getId()) {
                    if(reservation.getAvailableCount() - orderItemDTO.getItemCount() < 0) {
                        throw new RuntimeException("Cannot buy");
                    }
                    orderItemDTO.toDomain(order, reservation);

                    reservation.orderMenu(orderItemDTO.getItemCount());
                    log.debug("reservation : {}", reservation.getAvailableCount());
                }
            });
        });

        assertThat(order.getOrderItems().size()).isEqualTo(2);
        assertThat(order.getOrderTotalPrice()).isEqualTo(order.getOrderItems().stream().mapToInt(i -> i.getItemTotalPrice()).sum());

        orderService.save(order);

    }

//    [Service] storeId, 오늘 날짜를 가지고  ReservationMap 가져오기 (query 1번 호출)
//    orderItemDTOs 돌면서 해당 menuId,에 실판매갯수인 친구 업데이트
//    Reservation 안에 판매가능갯수랑 비교해서 안되면 error - throw error
//    OrderItem.setReservation(ReservationMap.get(menuId))
//            Order.add(OrderItem)
//    save(order);


    public OrderFormDTO init_data() {
        // When
        List<ReservationDTO> reservationDTOs = Arrays.asList(
                ReservationDTO.builder()
                        .maxCount(3)
                        .personalMaxCount(3)
                        .menuId(1L)
                        .build(),
                ReservationDTO.builder()
                        .maxCount(3)
                        .personalMaxCount(3)
                        .menuId(2L)
                        .build());
        ReservationFormDTO reservationFormDTO = ReservationFormDTO.builder()
                .hourToClose(12)
                .minuteToClose(0)
                .reservationDTOs(reservationDTOs)
                .build();
        store.deactivate();

        reservationService.createReservation(reservationFormDTO, store.getId());

        List<OrderItemDTO> orderItems = new ArrayList<>();
        OrderItemDTO orderItemDTO1 = new OrderItemDTO(1L, 1);
        OrderItemDTO orderItemDTO2 = new OrderItemDTO(2L, 1);
//        OrderItemDTO orderItemDTO3 = new OrderItemDTO(3L, 1);
//        OrderItemDTO orderItemDTO4 = new OrderItemDTO(4L, 1);
//        OrderItemDTO orderItemDTO5 = new OrderItemDTO(5L, 1);

        orderItems.add(orderItemDTO1);
        orderItems.add(orderItemDTO2);
//        orderItems.add(orderItemDTO3);
//        orderItems.add(orderItemDTO4);
//        orderItems.add(orderItemDTO5);

        return new OrderFormDTO("hong", "010", "1111", "1111", LocalDateTime.now(), orderItems);
    }
}
