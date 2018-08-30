package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.domain.StoreRepository;
import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.dto.StoreInputDTO;
import com.aoa.springwebservice.dto.StoreOutputDTO;
import com.aoa.springwebservice.dto.StoreUpdateInputDTO;
import com.aoa.springwebservice.exception.CustomerOrderException;
import com.aoa.springwebservice.property.MayakURLProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Service
public class StoreService {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    S3Uploader s3Uploader;

    //private static final String MAYAK_URL = MayakURLProperties;
    private String mayakUrl;

    @Autowired
    public StoreService(MayakURLProperties mayakURLProperties) {
        this.mayakUrl = mayakURLProperties.getUrl();
    }

    public Store createStore(StoreInputDTO storeDTO, User user) throws IOException {
        return storeRepository.save(storeDTO.toDomain(saveStoreImg(storeDTO), user));
    }
//    public String saveStoreImg(StoreInputDTO storeDTO) {
//        return fileStorageService.storeFile(storeDTO.getImageFile());
//    }

    public String saveStoreImg(StoreInputDTO storeDTO) throws IOException {
        return s3Uploader.upload(storeDTO.getImageFile(), "static");
    }

    public String updateStoreImg(StoreUpdateInputDTO storeDTO) throws IOException {
        if(storeDTO.getImageFile() == null){
            return storeDTO.getImgURL();
        }
        return s3Uploader.upload(storeDTO.getImageFile(), "static");
    }
    public boolean hasStore(User user) {
        return storeRepository.findByUserId(user.getId()).isPresent();
    }

    public Store getStoreByUser(User user){
        return storeRepository.findByUser(user).orElseThrow(() -> new EntityExistsException("가게 등록 부터 해주세요"));
    }
  
    public StoreOutputDTO createStoreDetailInfoDTO(Store store){
        return StoreOutputDTO.createStoreDetailInfoDTO(store);
    }

    public Store getStoreById(long id) {
        return storeRepository.findById(id).orElseThrow(() -> new CustomerOrderException("주문할 가게가 등록되지 않았습니다"));
    }

    public String makeOwnerUrl(User loginUser) {
        return mayakUrl + "/stores/" + getStoreByUser(loginUser).getId() + "/orders/form";

    }

    public Store updateStore(StoreUpdateInputDTO storeDTO, User user) throws IOException {
        Store store = storeRepository.findByUser(user).orElseThrow(() -> new EntityExistsException("가게 등록 부터 해주세요"));
        return storeRepository.save(store.updateStore(storeDTO.toDomain(updateStoreImg(storeDTO))));
    }
}
