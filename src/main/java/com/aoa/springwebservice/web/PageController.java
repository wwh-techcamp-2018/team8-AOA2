package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
