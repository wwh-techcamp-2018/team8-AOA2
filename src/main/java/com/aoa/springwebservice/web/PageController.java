package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.domain.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.format.DateTimeFormatter;

@Controller
public class PageController {
    @Autowired
    StoreRepository storeRepository;
    @GetMapping("menus")
    public String registMenu(Model model){
        //todo 가게 없을때 처리
        model.addAttribute("store", storeRepository.findById(1L).get());
        return "/registMenu";
    }
    @GetMapping("reservations")
    public String openReservation(Model model){
        //todo store 존재 확인, store isOpen 확인
        Store tempStore = storeRepository.findById(1L).get();
        model.addAttribute("storeId", tempStore.getId());
        model.addAttribute("timeToClose", tempStore.getTimeToClose().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        model.addAttribute("hourToClose", tempStore.getTimeToClose().toLocalTime().format(DateTimeFormatter.ofPattern("HH")));
        model.addAttribute("minuteToClose", tempStore.getTimeToClose().toLocalTime().format(DateTimeFormatter.ofPattern("mm")));
        //todo active menus 만 가져오도록
        model.addAttribute("menus", tempStore.getMenus());
    return "/openReservation";
    }
}
