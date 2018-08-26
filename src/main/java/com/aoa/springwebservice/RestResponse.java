package com.aoa.springwebservice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.net.URI;

@NoArgsConstructor
@Setter @Getter
public class RestResponse<T> {
    private T data;

    private RestResponse(T data) {
        this.data = data;
    }

    public static RestResponse<RedirectData> ofRedirectResponse(String url, String message){
        return new RestResponse<RedirectData>(new RedirectData(url, message));
    }
    @NoArgsConstructor @Setter @Getter
    public static class RedirectData {
        private String url;
        private String message;

        public RedirectData(String url, String message) {
            this.url = url;
            this.message = message;
        }
    }
}

