package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.*;
import com.aoa.springwebservice.domain.support.MenuDTO;
import com.aoa.springwebservice.domain.support.MenuOutputDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MenuService {
    @Autowired
    MenuRepository menuRepository;

    @Autowired
    StoreRepository storeRepository;


    public void createMenu(MenuDTO menuDTO, User user){
        log.debug("Menu DTO : {}", menuDTO);
        Store store = storeRepository.findByUser(user).get();
        Menu menu = menuDTO.toDomain(store);
        log.debug("Menu : {}", menu);
        store.addMenu(menu);
        storeRepository.save(store);
    }

    public List<MenuOutputDTO> findAllMenuInStore(User user) {
        Store store = storeRepository.findByUser(user).get();
//        log.debug("store : {}", store);
//        List<Menu> menuList = menuRepository.findAllByStore(store);
//        List<MenuOutputDTO> menuOutputDTOList = new ArrayList<>();
//        menuList.stream().forEach(e -> menuOutputDTOList.add(MenuOutputDTO.createMenuOutputDTO(e)));
        return store.getUsedMenuOutputDTOList();//store.getMenuOutputDTOList();
    }
    public List<MenuOutputDTO> findActiveMenuInStore(long storeId) {
        Store store = storeRepository.findById(storeId).get();
        return store.getUsedMenuOutputDTOList();
    }

    public Menu deleteMenu(long menuId) {
        Menu menu = menuRepository.findById(menuId).get();
        menu.deleteMenu();
        menuRepository.save(menu);
        return menu;
    }

}
