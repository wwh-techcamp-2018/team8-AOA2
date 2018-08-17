package com.aoa.springwebservice.dto;

import com.aoa.springwebservice.domain.Store;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter @NoArgsConstructor

public class InputStoreDTO {
    public static final String PHONE_NUMBER = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$";
    public static final String NAME = "^[가-힣]*$";

    @NotNull
    @Size(max = 20, message = "길이가 맞지 않습니다")
    //todo length, pattern 등 다른 조건들
    private String storeName;

    @NotNull
    @Size(max = 30, message = "길이가 맞지 않습니다")
    private String serviceDescription;

    @NotNull
    @Size(min = 2, max = 5, message = "길이가 맞지 않습니다")
    @Pattern(regexp = NAME, message = "입력양식이 맞지 않습니다")
    private String ownerName;

    @NotNull
    @Size(min = 5, max = 5, message = "길이가 맞지 않습니다")
    private String postCode;

    @NotNull
    @Size(max = 50, message = "길이가 맞지 않습니다")
    private String address;

    @Nullable
    @Size(max = 20, message = "길이가 맞지 않습니다")
    private String addressDetail;

    @NotNull
    @Size(max = 20, message = "길이가 맞지 않습니다")
    @Pattern(regexp = PHONE_NUMBER, message = "입력양식이 맞지 않습니다")
    private String phoneNumber;

    @Nullable
    @Size(max = 300, message = "길이가 맞지 않습니다")
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
