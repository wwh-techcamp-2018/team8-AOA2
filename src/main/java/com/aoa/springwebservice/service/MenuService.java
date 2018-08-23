package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.*;
import com.aoa.springwebservice.dto.MenuDTO;
import com.aoa.springwebservice.dto.MenuDTOToUpload;
import com.aoa.springwebservice.dto.MenuOutputDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
public class MenuService {
    @Autowired
    MenuRepository menuRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    FileStorageService fileStorageService;


    public void createMenu(MenuDTO menuDTO, User user) {
        log.debug("Menu DTO : {}", menuDTO);
        Store store = storeRepository.findByUser(user).get();
        Menu menu = menuDTO.toDomain(store);
        log.debug("Menu : {}", menu);
        store.addMenu(menu);
        storeRepository.save(store);
    }

    @Transactional
    public void createMenu(MenuDTOToUpload menuDTO, long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(
                        () -> new EntityNotFoundException("No Search Store By storeId : " + storeId)
                );
        String menuImgUrl = fileStorageService.storeFile(menuDTO.getFile());
        Menu menu = menuDTO.toDomain(store, menuImgUrl);
        store.addMenu(menu);
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

    public List<MenuOutputDTO> findAllMenuInStore(long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(
                        () -> new EntityNotFoundException("No Search Store By storeId : " + storeId)
                );
        return store.getMenuOutputDTOList();
    }

    @Transactional
    public Menu deleteMenu(long menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new EntityNotFoundException("No Search Store By menuId : " + menuId)
        );
        menu.deleteMenu();
        return menu;
    }

}
