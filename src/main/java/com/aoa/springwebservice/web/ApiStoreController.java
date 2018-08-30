package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.dto.StoreInputDTO;
import com.aoa.springwebservice.dto.StoreUpdateInputDTO;
import com.aoa.springwebservice.security.AuthorizedStore;
import com.aoa.springwebservice.security.LoginUser;
import com.aoa.springwebservice.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/stores")

public class ApiStoreController {
    static final Logger log = LoggerFactory.getLogger(ApiStoreController.class);
    @Autowired
    StoreService storeService;

    @PostMapping("")
    public String create(@Valid StoreInputDTO storeInputDTO, @LoginUser User loginUser)throws IOException {
        log.debug("inputDTO : {}", storeInputDTO);
        //todo store 생성실패 상황 고려
        if(storeService.hasStore(loginUser)){
            //return "/alreadyRegisted";
            return "/owner/stores/form";
        }
        Store store = storeService.createStore(storeInputDTO, loginUser);
        return "/owner/stores/reform";
    }

    @PostMapping("/update")
    public String update(@Valid StoreUpdateInputDTO storeDTO, @LoginUser User loginUser)throws IOException {
        log.debug("update DTO : {}", storeDTO);
        if(!storeService.hasStore(loginUser)){
            return "/owner/stores/form";
        }
        Store store = storeService.updateStore(storeDTO, loginUser);
        return "/owner/stores/reform";
    }
}
