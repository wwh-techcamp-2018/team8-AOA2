package com.aoa.springwebservice.dto;

import com.aoa.springwebservice.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class UserInputDTO {

    public static final String NAME = "^[가-힣]*$";
    public static final String EMAIL = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";
    public static final String PHONE_NUMBER_1 = "^01(?:0|1|[6-9])$";
    public static final String NUMBER = "[0-9]*";

    private String uuid;

    @NotNull
    @Size(min = 2, max = 5, message = "길이가 맞지 않습니다")
    @Pattern(regexp = NAME, message = "입력양식이 맞지 않습니다")
    private String name;

    @NotNull
    @Pattern(regexp = EMAIL, message = "이메일 양식이 맞지 않습니다")
    private String email;

    @NotNull
    @Size(max = 3, message = "길이가 맞지 않습니다")
    @Pattern(regexp = PHONE_NUMBER_1, message = "입력양식이 맞지 않습니다")
    private String phoneNumber_1;

    @NotNull
    @Size(max = 3, message = "길이가 맞지 않습니다")
    @Pattern(regexp = NUMBER, message = "입력양식이 맞지 않습니다")
    private String phoneNumber_2;

    @NotNull
    @Size(max = 3, message = "길이가 맞지 않습니다")
    @Pattern(regexp = NUMBER, message = "입력양식이 맞지 않습니다")
    private String phoneNumber_3;

    @Builder
    public UserInputDTO(String uuid, @NotNull @Size(min = 2, max = 5, message = "길이가 맞지 않습니다") @Pattern(regexp = NAME, message = "입력양식이 맞지 않습니다") String name, @NotNull @Pattern(regexp = EMAIL, message = "이메일 양식이 맞지 않습니다") String email, @NotNull @Size(max = 3, message = "길이가 맞지 않습니다") @Pattern(regexp = PHONE_NUMBER_1, message = "입력양식이 맞지 않습니다") String phoneNumber_1, @NotNull @Size(max = 3, message = "길이가 맞지 않습니다") @Pattern(regexp = NUMBER, message = "입력양식이 맞지 않습니다") String phoneNumber_2, @NotNull @Size(max = 3, message = "길이가 맞지 않습니다") @Pattern(regexp = NUMBER, message = "입력양식이 맞지 않습니다") String phoneNumber_3) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.phoneNumber_1 = phoneNumber_1;
        this.phoneNumber_2 = phoneNumber_2;
        this.phoneNumber_3 = phoneNumber_3;
    }

    public User toEntity() {
        return User.builder().uuid(uuid)
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber_1 + phoneNumber_2 + phoneNumber_3)
                .build();

    }


}
