package com.aoa.springwebservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Getter @NoArgsConstructor
@Entity
public class Store{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //todo length 등 다른 조건들
    @Column(nullable = false, length = 40)
    private String storeName;

    @Column(nullable = false, length = 60)
    private String serviceDescription;

    @Column(nullable = false, length = 10)
    private String ownerName;

    @Column(nullable = true, length = 300)
    private String imgURL;

    @Column(nullable = false, length = 5)
    private String postCode;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = true, length = 40)
    private String addressDetail;

    @Column(nullable = false, length = 11)
    private String phoneNumber;

    @Column(nullable = true, length = 600)
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
