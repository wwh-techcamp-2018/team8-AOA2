package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.Order;
import com.aoa.springwebservice.dto.OrderOutputDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class StoreSocket {
    @MessageMapping("presentOrders/{storeId}")
    @SendTo("/topic/stores/orders/{storeId}")
    public OrderOutputDTO updateRealTimeOrder(@DestinationVariable long storeId, @RequestBody OrderOutputDTO order, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        log.debug("storeId : {}", storeId);
        log.debug("order message : {}", order);
        //todo DTO 분리 또는 ExtendableDTO 사용
        return order;
    }

    @MessageMapping("toastOrders/{storeId}")
    @SendTo("/topic/stores/toasts/{storeId}")
    public OrderOutputDTO toastOrderInfo(@DestinationVariable long storeId, @RequestBody OrderOutputDTO order, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        log.debug("storeId : {}", storeId);
        log.debug("order message : {}", order);
        return order;
    }

}
