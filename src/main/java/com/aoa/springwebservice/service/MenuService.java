package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.Menu;
import com.aoa.springwebservice.domain.MenuRepository;
import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.domain.StoreRepository;
import com.aoa.springwebservice.domain.support.MenuDTO;
import com.aoa.springwebservice.domain.support.MenuOutputDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MenuService {
    @Autowired
    MenuRepository menuRepository;

    @Autowired
    StoreRepository storeRepository;


    public void createMenu(long storeId, MenuDTO menuDTO){
        log.debug("Menu DTO : {}", menuDTO);
        Store store = storeRepository.findById(storeId).get();
        Menu menu = menuDTO.toDomain(store);
        log.debug("Menu : {}", menu);
        store.addMenu(menu);
        storeRepository.save(store);
    }

    public List<MenuOutputDTO> findAllMenuInStore(long storeId) {
        Store store = storeRepository.findById(storeId).get();
        List<Menu> menuList = menuRepository.findAllByStore(store);
        List<MenuOutputDTO> menuOutputDTOList = new ArrayList<>();
        menuList.stream().forEach(e -> menuOutputDTOList.add(MenuOutputDTO.createMenuOutputDTO(e)));
        return menuOutputDTOList;
    }

}
