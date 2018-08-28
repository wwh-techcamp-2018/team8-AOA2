package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.Menu;
import com.aoa.springwebservice.dto.MenuDTOToUpload;
import com.aoa.springwebservice.dto.MenuOutputDTO;
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


    @PostMapping(path = "/stores/{storeId}/menus")
    public String createMenuWithStoreId(MenuDTOToUpload menuDTO, long storeId){
        menuService.createMenu(menuDTO, storeId);
        return "/result/success";
    }

    @GetMapping(path ="/stores/{storeId}/menus")
    public List<MenuOutputDTO> listActiveMenus(@RequestParam(required = false) String condition, @PathVariable long storeId){
        //todo condition 체크
        if(condition != null ) {
            log.debug("listActiveMenus");
            return menuService.getLastUsedMenusInStore(storeId);
        }
        log.debug("getAllMenus");
        return menuService.findAllMenuInStore(storeId);
    }

    @DeleteMapping(path = "/menus/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public Menu deleteMenu(@PathVariable long menuId){
        return menuService.deleteMenu(menuId);
    }
}