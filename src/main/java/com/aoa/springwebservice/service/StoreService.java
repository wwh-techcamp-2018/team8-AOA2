package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.domain.StoreRepository;
import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.dto.InputStoreDTO;
import com.aoa.springwebservice.dto.OutputStoreDTO;
import jdk.internal.util.xml.impl.Input;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    FileStorageService fileStorageService;

    public Store createStore(InputStoreDTO storeDTO, User user){
        return storeRepository.save(storeDTO.toDomain(somethingConvert(storeDTO), user));
    }

    public OutputStoreDTO convertToOutputDTO(Store store) {
        return OutputStoreDTO.createOutputDTO(store);
    }


    public String somethingConvert(InputStoreDTO storeDTO) {
        return fileStorageService.storeFile(storeDTO.getImageFile());
    }

    public boolean hasStore(User user) {
        return storeRepository.findByUserId(user.getId()).isPresent();
    }

}
