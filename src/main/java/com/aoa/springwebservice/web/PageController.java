package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.StoreRepository;
import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.security.LoginUser;
import com.aoa.springwebservice.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Autowired
    StoreService storeService;

    @GetMapping("/admin")
    public String show(@LoginUser User loginUser) {
        if(storeService.hasStore(loginUser))
            return "redirect:/result/success";

        return "/admin/store/fail";
    }
//    @GetMapping("menus")
//    public String registMenu(Model model){
//        //todo 가게 없을때 처리
//        model.addAttribute("store", storeRepository.findById(1L).get());
//        return "/registMenu";
//    }
}
