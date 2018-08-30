package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.Menu;
import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.dto.MenuDTOToUpload;
import com.aoa.springwebservice.dto.MenuOutputDTO;
import com.aoa.springwebservice.security.AuthorizedStore;
import com.aoa.springwebservice.service.FileStorageService;
import com.aoa.springwebservice.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public String createMenuWithStoreId(MenuDTOToUpload menuDTO,  @AuthorizedStore Store store) throws IOException {
        menuService.createMenu(menuDTO, store);
        return "/owner/menus";
        //return "/result/success";
    }

    @GetMapping(path ="/stores/{storeId}/menus")
    public List<MenuOutputDTO> listActiveMenus(@RequestParam(required = false) String condition, @AuthorizedStore Store store){
        //todo condition 체크
        if(condition != null ) {
            log.debug("listActiveMenus");
            return menuService.getLastUsedMenusInStore(store);
        }
        log.debug("getAllMenus");
        return menuService.findAllMenuInStore(store);
    }

    @DeleteMapping(path = "/stores/{storeId}/menus/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public Menu deleteMenu(@AuthorizedStore Store store, @PathVariable long menuId){
        return menuService.deleteMenu(store, menuId);
    }
}