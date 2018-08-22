package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.Menu;
import com.aoa.springwebservice.domain.MenuRepository;
import com.aoa.springwebservice.domain.support.MenuDTO;
import com.aoa.springwebservice.domain.support.MenuDTOToUpload;
import com.aoa.springwebservice.domain.support.MenuOutputDTO;
import com.aoa.springwebservice.dto.ReservationFormDTO;
import com.aoa.springwebservice.service.FileStorageService;
import com.aoa.springwebservice.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiMenuController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private MenuService menuService;

    @PostMapping(path = "/stores/{storeId}/menus/")
    public String createMenu(@PathVariable long storeId,  MenuDTOToUpload menuDTO) {
        log.debug("menuDTO : {}", menuDTO);
        log.debug("file : {}", menuDTO.getFile());
        String menuImgUrl = fileStorageService.storeFile(menuDTO.getFile());
        menuDTO.setImageUrl(menuImgUrl);
        menuService.createMenu(storeId, menuDTO);
        return "/result/success";
    }

    @GetMapping(path = "/owner/{storeId}/menu")
    public List<MenuOutputDTO> getAllMenu(@PathVariable long storeId) {
        return menuService.findAllMenuInStore(storeId);
    }

    @DeleteMapping(path = "/owner/menu/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public Menu deleteMenu(@PathVariable long menuId){
        return menuService.deleteMenu(menuId);
    }
    @GetMapping("/owner/{storeId}/menus/active")
    public List<MenuOutputDTO> listActiveMenus(@PathVariable long storeId){
        return menuService.findActiveMenuInStore(storeId);
    }
}