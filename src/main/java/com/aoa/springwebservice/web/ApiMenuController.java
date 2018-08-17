package com.aoa.springwebservice.web;

import com.aoa.springwebservice.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiMenuController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(path = "/menu")
    public String createMenu(MultipartFile file) {

        String menuImgUrl = fileStorageService.storeFile(file);
        log.debug("menuImgUrl : {}", menuImgUrl);
        return "";
    }
}