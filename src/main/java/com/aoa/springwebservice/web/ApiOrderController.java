package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.Order;
import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.dto.ExtendableDTO;
import com.aoa.springwebservice.dto.OrderFormDTO;
import com.aoa.springwebservice.dto.OrderItemDTO;
import com.aoa.springwebservice.dto.OrderOutputDTO;
import com.aoa.springwebservice.security.AuthorizedStore;
import com.aoa.springwebservice.service.OrderService;
import com.aoa.springwebservice.service.ReservationService;
import com.aoa.springwebservice.service.StoreService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiOrderController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/stores/{storeId}/orders")
    public OrderOutputDTO makePayment(@PathVariable long storeId, @RequestBody OrderFormDTO orderFormDTO) {
        Order order = orderFormDTO.toDomain(storeService.getStoreById(storeId));
        //todo : parameter 3개...refactor 필요
        order = orderService.createOrder(reservationService.getTodayReservations(storeId), orderFormDTO, order);
        //todo refactor - dto? or domain
//        ExtendableDTO dto = new ExtendableDTO();
//        dto.add("name", order.getCustomer().getName());
//        dto.add("phoneNumber", order.getCustomer().getPhoneNumber());
//        dto.add("pickupTime", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(order.getPickupTime()));
//        dto.add("orderTotalPrice", order.getOrderTotalPrice());
//        dto.add("id", order.getId());
//        dto.add("orderItems", order.getOrderItems());
        return OrderOutputDTO.createOrderOutputDTO(order);
    }

    @PostMapping("/stores/{storeId}/orders/{orderId}")
    public Order updateIsPickedupStatus(@AuthorizedStore Store store, @PathVariable long orderId, @RequestBody Order order){
        log.debug("pickedupStatus : {}", order);
        return orderService.updateIsPickedupStatus(store, orderId, order);
    }

    @GetMapping(value = "/stores/{storeId}/orders", params="pickupDate")
    public List<OrderOutputDTO> getCurrentOrders(@AuthorizedStore Store store, @RequestParam String pickupDate) {
//        LocalDate pickUpDate = store.getTimeToClose().toLocalDate().plusDays(1); // + 1
        return orderService.selectOrders(store, LocalDate.parse(pickupDate).atTime(0, 0, 0))
                .stream()
                .map(order -> OrderOutputDTO.createOrderOutputDTO(order))
                .collect(Collectors.toList());
    }
}
