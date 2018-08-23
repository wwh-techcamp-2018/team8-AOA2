package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.domain.StoreRepository;
import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.dto.StoreInputDTO;
import com.aoa.springwebservice.dto.StoreOutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    FileStorageService fileStorageService;

    public Store createStore(StoreInputDTO storeDTO, User user){
        return storeRepository.save(storeDTO.toDomain(saveStoreImg(storeDTO), user));
    }

    public StoreOutputDTO convertToOutputDTO(Store store) {
        return StoreOutputDTO.createOutputDTO(store);
    }


    public String saveStoreImg(StoreInputDTO storeDTO) {
        return fileStorageService.storeFile(storeDTO.getImageFile());
    }

    public boolean hasStore(User user) {
        return storeRepository.findByUserId(user.getId()).isPresent();
    }

}
