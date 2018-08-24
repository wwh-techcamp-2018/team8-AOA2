package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.Menu;
import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.dto.MenuDTOToUpload;
import com.aoa.springwebservice.dto.MenuOutputDTO;
import com.aoa.springwebservice.security.LoginUser;
import com.aoa.springwebservice.service.FileStorageService;
import com.aoa.springwebservice.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiMenuController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private MenuService menuService;

    //todo deprecated _ @Deprecated
    @PostMapping(path = "/owner/menus")
    public String createMenu(MenuDTOToUpload menuDTO, @LoginUser User user) {
        log.debug("menuDTO : {}", menuDTO);
        log.debug("file : {}", menuDTO.getFile());
        String menuImgUrl = fileStorageService.storeFile(menuDTO.getFile());
        menuDTO.setImageUrl(menuImgUrl);
        menuService.createMenu(menuDTO, user);
        return "/result/success";
    }

    @PostMapping(path = "/stores/{storeId}/menus")
    public String createMenuWithStoreId(MenuDTOToUpload menuDTO, long storeId){
        menuService.createMenu(menuDTO, storeId);
        return "/result/success";
    }

    //todo deprecated _ @Deprecated
    @GetMapping(path = "/owner/menus")
    public List<MenuOutputDTO> getAllMenu(@LoginUser User user) {
        return menuService.findAllMenuInStore(user);
    }

    @GetMapping(path = "/stores/{storeId}/menus")
    public List<MenuOutputDTO> getAllMenu(@PathVariable long storeId){
        return menuService.findAllMenuInStore(storeId);
    }
    @GetMapping(path ="/stores/{storeId}/menus/", params = "condition")
    public List<MenuOutputDTO> listActiveMenus(@RequestParam final String condition, @PathVariable long storeId){
        //todo condition 체크
        return menuService.getLastUsedMenusInStore(storeId);
    }

    @DeleteMapping(path = "/menus/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public Menu deleteMenu(@PathVariable long menuId){
        return menuService.deleteMenu(menuId);
    }
}