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

    public RestResponse(T data) {
        this.data = data;
    }

    public static RestResponse<RedirectData> ofRedirectResponse(URI url, String message){
        return new RestResponse<RedirectData>(new RedirectData(url, message));
    }
    @NoArgsConstructor @Setter @Getter
    public static class RedirectData {
        private URI url;
        private String message;

        public RedirectData(URI url1, String message) {
            this.url = url;
            this.message = message;
        }
    }
}

