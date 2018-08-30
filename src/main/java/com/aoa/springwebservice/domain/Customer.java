package com.aoa.springwebservice.domain;


import com.aoa.springwebservice.converter.NumberConverter;
import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
@ToString
public class Customer {

    private String name;

    private String phoneNumber;

    @Builder
    public Customer(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    @JsonGetter("phoneNumber")
    public String getFormattedPhoneNumber(){
        return NumberConverter.formatPhoneNumber(phoneNumber);
    }
}
