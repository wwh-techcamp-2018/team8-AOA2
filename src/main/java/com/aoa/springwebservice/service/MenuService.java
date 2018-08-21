package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.Menu;
import com.aoa.springwebservice.domain.MenuRepository;
import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.domain.StoreRepository;
import com.aoa.springwebservice.domain.support.MenuDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    StoreRepository storeRepository;


    public void createMenu(long storeId, MenuDTO menuDTO){
        Store store = storeRepository.findById(storeId).get();
        Menu menu = menuDTO.toDomain(store);
        store.addMenu(menu);
        storeRepository.save(store);
    }

}
