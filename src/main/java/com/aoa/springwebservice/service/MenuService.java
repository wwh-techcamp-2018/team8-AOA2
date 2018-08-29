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
import java.io.IOException;
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

    @Autowired
    S3Uploader s3Uploader;


    public void createMenu(MenuDTO menuDTO, User user) {
        log.debug("Menu DTO : {}", menuDTO);
        Store store = storeRepository.findByUser(user).get();
        Menu menu = menuDTO.toDomain(store);
        log.debug("Menu : {}", menu);
        store.addMenu(menu);
        storeRepository.save(store);
    }

    @Transactional
    public void createMenu(MenuDTOToUpload menuDTO, Store store) throws IOException {
        String menuImgUrl = s3Uploader.upload(menuDTO.getFile(), "static");
        Menu menu = menuDTO.toDomain(store, menuImgUrl);
        store.addMenu(menu);
        storeRepository.save(store);
    }

    public List<MenuOutputDTO> findAllMenuInStore(User user) {
        Store store = storeRepository.findByUser(user).get();
        return store.getUsedMenuOutputDTOList();
    }
    //todo cacheable, cacheEvict on reservation registration
    public List<MenuOutputDTO> getLastUsedMenusInStore(Store store) {
        return store.getUsedMenuOutputDTOList();
    }

    //todo cacheable, cacheEvict on reservation registration
    public List<MenuOutputDTO> findAllMenuInStore(Store store) {
        return store.getMenuOutputDTOList();
    }

    @Transactional
    public Menu deleteMenu(Store store, long menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new EntityNotFoundException("No Search Store By menuId : " + menuId)
        );
        if(!menu.hasSameStore(store)){
            throw new RuntimeException("메뉴 삭제 권한이 없습니다");
        }
        menu.deleteMenu();
        return menu;
    }
    private Store getStoreByStoreId(long storeId){
        return storeRepository.findById(storeId)
                .orElseThrow(
                        () -> new EntityNotFoundException("No Search Store By storeId : " + storeId)
                );
    }
}
