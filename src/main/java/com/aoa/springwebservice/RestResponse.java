package com.aoa.springwebservice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
public class RestResponse<T> {
    private T data;

    public RestResponse(T data) {
        this.data = data;
    }
}
