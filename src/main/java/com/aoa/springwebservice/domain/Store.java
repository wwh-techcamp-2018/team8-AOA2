package com.aoa.springwebservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Getter @NoArgsConstructor
@Entity
public class Store{

    public static final String PHONE_NUMBER = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //todo length 등 다른 조건들
    @Column(nullable = false, length = 20)
    private String storeName;

    @Column(nullable = false)
    private String serviceDescription;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = true)
    private String imgURL;

    @Column(nullable = false)
    private String postCode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = true)
    private String addressDetail;

    @Column(nullable = false)
    @Pattern(regexp = PHONE_NUMBER)
    private String phoneNumber;

    @Column(nullable = true)
    private String description;

    @Builder
    public Store(String storeName, String serviceDescription, String ownerName, String imgURL, String postCode, String address, String addressDetail, String phoneNumber, String description) {
        this.storeName = storeName;
        this.serviceDescription = serviceDescription;
        this.ownerName = ownerName;
        this.imgURL = imgURL;
        this.postCode = postCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }

    //    public static Store builderDefault(String storeName, String ownerName, String postCode, String address, String phoneNumber) {
//        this.storeName = storeName;
//        this.ownerName = ownerName;
//        this.postCode = postCode;
//        this.address = address;
//        this.phoneNumber = phoneNumber;
//    }
}
