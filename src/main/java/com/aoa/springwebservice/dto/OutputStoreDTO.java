package com.aoa.springwebservice.dto;

import com.aoa.springwebservice.domain.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class OutputStoreDTO {

    private String storeName;

    private String serviceDescription;

    private String ownerName;

    private String postCode;

    private String address;

    private String addressDetail;

    private String phoneNumber;

    private String description;

    private String imgURL;
    @Builder
    public OutputStoreDTO(String storeName, String serviceDescription, String ownerName, String postCode, String address, String addressDetail, String phoneNumber, String description, String imgURL) {
        this.storeName = storeName;
        this.serviceDescription = serviceDescription;
        this.ownerName = ownerName;
        this.postCode = postCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.imgURL = imgURL;
    }

    public static OutputStoreDTO createOutputDTO(Store store) {
        //hint builder static
        return  new OutputStoreDTO( store.getStoreName(), store.getServiceDescription(), store.getOwnerName(),store.getPostCode(),store.getAddress(),store.getAddressDetail(),store.getPhoneNumber(), store.getDescription(), store.getImgURL());
    }

}
