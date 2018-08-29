package com.aoa.springwebservice.dto;

import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter @NoArgsConstructor

public class StoreUpdateInputDTO {
    public static final String NAME = "^[가-힣]*$";
    public static final String PHONE_NUMBER_1 = "^01(?:0|1|[6-9])$";
    public static final String Number = "[0-9]*";
    @NotNull
    @Size(max = 30, message = "길이가 맞지 않습니다")
    private String serviceDescription;

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
    @Size(max = 3, message = "길이가 맞지 않습니다")
    @Pattern(regexp = PHONE_NUMBER_1, message = "입력양식이 맞지 않습니다")
    private String phoneNumber_1;

    @NotNull
    @Size(min = 3, max = 4, message = "길이가 맞지 않습니다")
    @Pattern(regexp = Number, message = "입력양식이 맞지 않습니다")
    private String phoneNumber_2;

    @NotNull
    @Size(min = 4, max = 4, message = "길이가 맞지 않습니다")
    @Pattern(regexp = Number, message = "입력양식이 맞지 않습니다")
    private String phoneNumber_3;

    @Nullable
    @Size(max = 300, message = "길이가 맞지 않습니다")
    private String description;

    private String imgURL;

    private MultipartFile imageFile;
    @Builder
    public StoreUpdateInputDTO(String serviceDescription, String postCode, String address, String addressDetail, String phoneNumber_1, String phoneNumber_2, String phoneNumber_3, String description, String imgURL, MultipartFile imageFile) {
        this.serviceDescription = serviceDescription;
        this.postCode = postCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.phoneNumber_1 = phoneNumber_1;
        this.phoneNumber_2 = phoneNumber_2;
        this.phoneNumber_3 = phoneNumber_3;
        this.description = description;
        this.imgURL = imgURL;
        this.imageFile = imageFile;
    }

    //todo refactoring
    public Store toDomain(String imgUrl){
        return Store.builder().serviceDescription(serviceDescription)
                .postCode(postCode)
                .address(address)
                .addressDetail(addressDetail)
                .phoneNumber(phoneNumber_1.concat(phoneNumber_2).concat(phoneNumber_3))
                .description(description)
                .imgURL(imgUrl).build();
    }
}
