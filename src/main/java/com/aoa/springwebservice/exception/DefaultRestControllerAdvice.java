package com.aoa.springwebservice.exception;


import com.aoa.springwebservice.RestResponse;
import com.aoa.springwebservice.response.ApiError;
import com.aoa.springwebservice.response.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@Order(1)
public class DefaultRestControllerAdvice {

    @ExceptionHandler({RuntimeException.class, EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse<ApiError> defaultExceptionHandler(RuntimeException exception) {
        log.debug("RuntimeException {}", exception.getMessage());
        exception.printStackTrace();
        return RestResponse.ofErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), exception.getMessage());
    }

    @ExceptionHandler(FileStorageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String fileStorageExceptionHandler(FileStorageException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ValidationErrorResponse handleBindException2(BindException ex, HttpServletRequest request,
                                                           HttpServletResponse response, @Nullable Object handler) throws IOException {
        log.debug("handleBindException 2");
        log.debug("handler {}", handler.getClass());

        return new ValidationErrorResponse(new ApiError("ERROR MSG", ex.getLocalizedMessage()))
                .addAllErrors(ex.getBindingResult());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public String unAuthorized(UnAuthorizedException exception) {
        log.debug("unAuthorized : {}", exception);
        return exception.getMessage();
    }
}