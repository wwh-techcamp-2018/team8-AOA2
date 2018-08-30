package com.aoa.springwebservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice(annotations = Controller.class)
@Order(2)
@Slf4j
public class DefaultControllerAdvice {

    private static final String DEFAULT_LOGIN_ERROR_PAGE_URI = "/forbidden";
    private static final String DEFAULT_ERROR_PAGE_URI = "/fail";
    private static final String ATTRIBUTE_NAME_FOR_ERROR_MESSAGE = "errorMessage";

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public String unAuthorized(UnAuthorizedException exception, Model model) {
        log.debug("unAuthorized : {}", exception.getMessage());
        return DEFAULT_LOGIN_ERROR_PAGE_URI;
    }

    @ExceptionHandler(CustomerOrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String customerOrderExceptionHandler(CustomerOrderException exception, Model model) {
        log.debug("Customer Order Exception : {}", exception);
        model.addAttribute(ATTRIBUTE_NAME_FOR_ERROR_MESSAGE, exception.getMessage());
        return "/customerError";
    }

    @ExceptionHandler({
            FileStorageException.class,
            RuntimeException.class,
            EntityNotFoundException.class,
            MethodArgumentNotValidException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String defaultExceptionHandler(RuntimeException exception, Model model) {
        log.debug("RuntimeException {}", exception.getMessage());
        model.addAttribute(ATTRIBUTE_NAME_FOR_ERROR_MESSAGE, exception.getMessage());
        return DEFAULT_ERROR_PAGE_URI;
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected String handleBindException(BindException exception, Model model) {
        model.addAttribute(ATTRIBUTE_NAME_FOR_ERROR_MESSAGE, exception.getBindingResult().getAllErrors().toString());
        return DEFAULT_ERROR_PAGE_URI;
    }

}
