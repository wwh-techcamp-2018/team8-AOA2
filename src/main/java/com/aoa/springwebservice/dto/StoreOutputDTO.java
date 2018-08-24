package com.aoa.springwebservice.dto;

import com.aoa.springwebservice.domain.Store;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter @Setter @NoArgsConstructor @ToString
public class StoreOutputDTO {

    private String storeName;

    private String serviceDescription;

    private String ownerName;

    private String postCode;

    private String address;

    private String addressDetail;
    //todo phoneNumber 필요시, 1,2,3으로 쪼개기
    private String phoneNumber;

    private String description;

    private String imgURL;

    private long storeId;

    private String timeToClose;

    private String hourToClose;

    private String minuteToClose;

    @Builder
    public StoreOutputDTO(String storeName, String serviceDescription, String ownerName, String postCode, String address, String addressDetail, String phoneNumber, String description, String imgURL, long storeId, LocalDateTime timeToClose) {
        this.storeName = storeName;
        this.serviceDescription = serviceDescription;
        this.ownerName = ownerName;
        this.postCode = postCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.imgURL = imgURL;
        this.storeId = storeId;
        this.timeToClose = timeToClose.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        this.hourToClose =  timeToClose.toLocalTime().format(DateTimeFormatter.ofPattern("HH"));
        this.minuteToClose = timeToClose.toLocalTime().format(DateTimeFormatter.ofPattern("mm"));
    }

    public static StoreOutputDTO createStoreDetailDTO(@NotNull Store store) {
        return  StoreOutputDTO.builder()
                .storeName(store.getStoreName())
                .serviceDescription(store.getServiceDescription())
                .ownerName(store.getOwnerName())
                .postCode(store.getPostCode())
                .address(store.getAddress())
                .addressDetail(store.getAddressDetail())
                .phoneNumber(store.getPhoneNumber())
                .description(store.getDescription())
                .imgURL(store.getImgURL())
                .storeId(store.getId())
                .build();
    }

    public static StoreOutputDTO createStoreOpenInfoDTO(@NotNull Store store) {
        return  StoreOutputDTO.builder()
                .storeName(store.getStoreName())
                .storeId(store.getId())
                .timeToClose(
                        Optional.ofNullable(store.getTimeToClose())
                                .orElse( LocalDateTime.of(LocalDate.now(),LocalTime.of(LocalTime.now().getHour(), ((LocalTime.now().getMinute()/ 30) * 30))).plusMinutes(30)
                                )
                )
                .build();
    }


}
