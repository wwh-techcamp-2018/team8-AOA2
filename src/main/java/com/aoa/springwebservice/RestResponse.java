package com.aoa.springwebservice;

import com.aoa.springwebservice.response.ApiError;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@NoArgsConstructor
@Setter @Getter
public class RestResponse<T> {
    @JsonUnwrapped
    private T data;
    private HttpStatus status;

    private RestResponse(HttpStatus status, T data) {
        this.status = status;
        this.data = data;
    }

    public static RestResponse<RedirectData> ofRedirectResponse(String url, String message){
        return new RestResponse<RedirectData>(HttpStatus.OK, new RedirectData(url, message));
    }
    public static RestResponse<ApiError> ofErrorResponse(HttpStatus status, String errorMsg, String debugMsg){
        return new RestResponse<ApiError>(status, new ApiError(errorMsg, debugMsg));
    }

    @NoArgsConstructor @Setter @Getter
    public static final class RedirectData {
        private String url;
        private String message;

        public RedirectData(String url, String message) {
            this.url = url;
            this.message = message;
        }
    }
//    @NoArgsConstructor @Setter @Getter
//    public static final class ApiErrorData {
//        private HttpStatus status;
//        private String error_code;
//        private String message;
//        private String detail;
//        @Builder
//        public ApiErrorData(HttpStatus status, String error_code, String message, String detail) {
//            this.status = status;
//            this.error_code = error_code;
//            this.message = message;
//            this.detail = detail;
//        }
//    }
}

