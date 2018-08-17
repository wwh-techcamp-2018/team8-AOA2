package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.dto.InputStoreDTO;
import com.aoa.springwebservice.dto.OutputStoreDTO;
import com.aoa.springwebservice.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/stores")
@Slf4j
public class ApiStoreController {
    @Autowired
    StoreService storeService;

    @PostMapping("")
    public ResponseEntity<Void> create(@Valid InputStoreDTO inputStoreDTO){
        log.debug("inputDTO : {}", inputStoreDTO);
        //todo store 생성실패 상황 고려
        Store store = storeService.createStore(inputStoreDTO);
        return ResponseEntity.created(URI.create("/")).build();
    }
}
