package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.Menu;
import com.aoa.springwebservice.domain.MenuRepository;
import com.aoa.springwebservice.domain.support.MenuDTOToUpload;
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
    private MenuRepository menuRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(path = "/menu")
    public String createMenu(MenuDTOToUpload menuDTO) {

        log.debug("menuDTO : {}", menuDTO);
        log.debug("file : {}", menuDTO.getFile());

        String menuImgUrl = fileStorageService.storeFile(menuDTO.getFile());
        menuDTO.setImageUrl(menuImgUrl);
        Menu menu = menuDTO.toDomain();

        log.debug("menu : {}", menu);

        menuRepository.save(menu);

        return "";
    }
}