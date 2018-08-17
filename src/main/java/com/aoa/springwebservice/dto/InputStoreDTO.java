package com.aoa.springwebservice.dto;

import com.aoa.springwebservice.domain.Store;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter @Setter @NoArgsConstructor

public class InputStoreDTO {

    @NotNull
    //todo length, pattern 등 다른 조건들
    private String storeName;

    @NotNull
    private String serviceDescription;

    @NotNull
    private String ownerName;

    @NotNull
    private String postCode;

    @NotNull
    private String address;

    @Nullable
    private String addressDetail;

    @NotNull
    private String phoneNumber;

    @Nullable
    private String description;

    @Nullable
    private MultipartFile imageFile;

    @Builder
    public InputStoreDTO(@NotNull String storeName, @NotNull String serviceDescription, @NotNull String ownerName, @NotNull String postCode, @NotNull String address, String addressDetail, @NotNull String phoneNumber, String description, MultipartFile imageFile) {
        this.storeName = storeName;
        this.serviceDescription = serviceDescription;
        this.ownerName = ownerName;
        this.postCode = postCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.imageFile = imageFile;
    }

    //todo refactoring
    public Store toDomain(String imgUrl){
        return Store.builder().storeName(storeName)
                .serviceDescription(serviceDescription)
                .ownerName(ownerName)
                .postCode(postCode)
                .address(address)
                .addressDetail(addressDetail)
                .phoneNumber(phoneNumber)
                .description(description)
                .imgURL(imgUrl).build();
    }
}
