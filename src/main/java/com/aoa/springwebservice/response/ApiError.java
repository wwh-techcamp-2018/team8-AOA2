package com.aoa.springwebservice.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter @Setter @NoArgsConstructor @ToString

public class ApiError {
    private String errorMsg;
    @JsonIgnore
    private String debugErrorMsg;
    @Builder
    public ApiError(String errorMsg, String debugErrorMsg) {
        this.errorMsg = errorMsg;
        this.debugErrorMsg = debugErrorMsg;
    }
}
