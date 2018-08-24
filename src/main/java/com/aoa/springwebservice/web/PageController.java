package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.Reservation;
import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.domain.StoreRepository;
import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.security.HttpSessionUtils;
import com.aoa.springwebservice.security.LoginUser;
import com.aoa.springwebservice.service.ReservationService;
import com.aoa.springwebservice.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@Slf4j
public class PageController {

    @Autowired
    StoreService storeService;

    @Autowired
    ReservationService reservationService;

    @GetMapping("/admin")
    public String show(@LoginUser User loginUser) {
        if (storeService.hasStore(loginUser))
            return "redirect:/result/success";

        return "/admin/store/fail";
    }

    @GetMapping("/owner/reservations/form")
    public String openReservation(Model model, @LoginUser User loginUser) {
        //todo store 존재 확인, store isOpen 확인 --> 중복 로직 처리 어떻게?
        model.addAttribute("store", storeService.createStoreOpenInfoDTO(storeService.getStoreByUser(loginUser)));
      return "/openReservation";
    }

    @GetMapping(path = "/owner/reservations", params = "condition")
    public String showReservations(@RequestParam final String condition, @LoginUser User loginUser, Model model) {
        List<Reservation> reservations = reservationService.getReservationsByCondition(condition, storeService.getStoreByUser(loginUser));
        model.addAttribute("reservations", reservations);
        return "/displayReservation";
    }

//    @GetMapping("/stores/{storeId}/orders/form")
//    public String createOrder(@PathVariable long storeId, @LoginUser User loginUser, Model model){
//        //todo store 존재 확인, store isOpen 확인
//        model.addAttribute("store", storeService.createStoreOpenInfoDTO(storeService.getStoreByUser(loginUser)));
//        LocalTime now = LocalTime.now();
//        model.addAttribute("defaultTime", LocalTime.of(now.getHour(), ((now.getMinute()/ 30)) * 30).plusMinutes(30));
//        return "/createOrder";
//    }

    @Autowired
    StoreRepository storeRepository;
    @GetMapping("/stores/{storeId}/orders/form")
    public String createOrder(@PathVariable long storeId, Model model){
        //todo store 존재 확인, store isOpen 확인
        model.addAttribute("store", storeService.createStoreOpenInfoDTO(storeRepository.findById(storeId).get()));
        LocalTime now = LocalTime.now();
        model.addAttribute("defaultTime", LocalTime.of(now.getHour(), ((now.getMinute()/ 30)) * 30).plusMinutes(30));
        return "/createOrder";
    }
}
