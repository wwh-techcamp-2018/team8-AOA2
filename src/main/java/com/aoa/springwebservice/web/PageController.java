package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.*;
import com.aoa.springwebservice.exception.CustomerOrderException;
import com.aoa.springwebservice.security.HttpSessionUtils;
import com.aoa.springwebservice.security.LoginUser;
import com.aoa.springwebservice.service.OrderService;
import com.aoa.springwebservice.service.ReservationService;
import com.aoa.springwebservice.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@Slf4j
public class PageController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private OrderService orderService;


    @GetMapping("/")
    public String showBySigninStatus(HttpSession session) {
        if(HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/owner/stores/form";
        }
        return "/signin";
    }

    @GetMapping("/admin")
    public String show(@LoginUser User loginUser, Model model) {
        if (storeService.hasStore(loginUser))
            return alreadyRegistedStore(loginUser, model);
        return "/registStore";
    }

    @GetMapping("/owner/stores/form")
    public String registStore(@LoginUser User loginUser, Model model) {
        if(storeService.hasStore(loginUser))
            return alreadyRegistedStore(loginUser, model);

        model.addAttribute("navTitle", "가게정보 등록");
        return "/registStore";
    }

    @GetMapping("/owner/stores/reform")
    public String updateStore(@LoginUser User loginUser, Model model) {
        if(!storeService.hasStore(loginUser)) {
            return "redirect:/owner/stores/form";
        }

        model.addAttribute("navTitle", "가게정보 수정");
        model.addAttribute("store", storeService.createStoreDetailInfoDTO(storeService.getStoreByUser(loginUser)));
        return "/updateStore";
    }

    @GetMapping("/owner/menus/form")
    public String registMenu(@LoginUser User loginUser, Model model) {
        if(!storeService.hasStore(loginUser)) {
            throw new RuntimeException("가게 등록부터 해주세요.");
        }
        model.addAttribute("store", storeService.createStoreDetailInfoDTO(storeService.getStoreByUser(loginUser)));
        model.addAttribute("navTitle", "메뉴 등록");
        return "/registMenu";
    }

    @GetMapping("/owner/reservations/form")
    public String openReservation(Model model, @LoginUser User loginUser) {
        
        //todo store 존재 확인, store isOpen 확인 --> 중복 로직 처리 어떻게?
        if(!storeService.hasStore(loginUser)) {
            throw new RuntimeException("가게 등록부터 해주세요.");
        }
        Store store = storeService.getStoreByUser(loginUser);
        if(store.isOpen()) throw new RuntimeException("이미 진행 중인 예약이 존재합니다.");

        model.addAttribute("store", storeService.createStoreDetailInfoDTO(store));
        model.addAttribute("navTitle", "예약 등록");
//        LocalTime now = LocalTime.now();
//        model.addAttribute("defaultTime", LocalTime.of(now.getHour(), ((now.getMinute()/ 30)) * 30).plusMinutes(30));

        return "/openReservation";
    }

    @GetMapping(path = "/owner/reservations", params = "condition")
    public String showReservations(@RequestParam final String condition, @LoginUser User loginUser, Model model) {
        Store store = storeService.getStoreByUser(loginUser);
        List<Reservation> reservations = reservationService.getReservationsByCondition(condition, store);
        model.addAttribute("reservations", reservations);
        model.addAttribute("pickUpDate", store.getTimeToClose().toLocalDate().plusDays(1));
        model.addAttribute("store", store);
        model.addAttribute("navTitle", "예약 조회");
        return "/displayReservation";
    }

    @GetMapping("/stores/{storeId}/orders/form")
    public String createOrder(@PathVariable long storeId, Model model){
        //todo store 존재 확인, store isOpen 확인
        Store store = storeService.getStoreById(storeId);
        if(!store.isOpen()) throw new CustomerOrderException("진행 중인 예약이 없습니다");

        model.addAttribute("store", storeService.createStoreDetailInfoDTO(storeService.getStoreById(storeId)));
        LocalTime now = LocalTime.now();
        model.addAttribute("defaultTime", LocalTime.of(now.getHour(), ((now.getMinute()/ 30)) * 30).plusMinutes(30));
        return "/createOrder";
    }
  
    @GetMapping("/owner/menus")
    public String showMenus(@LoginUser User user,  Model model) {
        if(!storeService.hasStore(user)) {
            throw new RuntimeException("가게 등록부터 해주세요.");
        }
        model.addAttribute("store", storeService.getStoreByUser(user));
        model.addAttribute("navTitle", "메뉴 조회");
        return "displayMenu";
    }

    @GetMapping("/owner/orders")
    public String openOrders(Model model, @LoginUser User loginUser) {
        Store store = storeService.getStoreByUser(loginUser);
        log.debug("store check {} ", store);
//        LocalDate lastDay = reservationService.getLastDay(storeService.getStoreByUser(loginUser));
//        log.debug("last day : {}", lastDay);
//        log.debug("orders : {}", orderService.selectOrders(store, lastDay.atTime(0,0,0)));

        if(!store.isOpen()) throw new RuntimeException("현재 진행 중인 예약이 없습니다.");

        LocalDate pickupDate = store.getTimeToClose().toLocalDate(); // + 1
        model.addAttribute("pickupDate", pickupDate);
        model.addAttribute("store", storeService.getStoreByUser(loginUser));
        model.addAttribute("orders", orderService.selectOrders(store, pickupDate.atTime(0,0,0)));

        return "/showOrders";
    }


    private String alreadyRegistedStore(User loginUser, Model model) {
        model.addAttribute("ownerUrl", storeService.makeOwnerUrl(loginUser));
        return "/alreadyRegisted";
    }
}
