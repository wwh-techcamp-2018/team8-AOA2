package com.aoa.springwebservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice//(basePackages = {"/web"})
@Slf4j
public class DefaultControllerAdvice {

    private static final String DEFAULT_ERROR_PAGE_URI = "/defaultErrorPage";
    private static final String ATTRIBUTE_NAME_FOR_ERROR_MESSAGE = "errorMessage";

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public String unAuthorized(UnAuthorizedException exception, Model model) {
        log.debug("unAuthorized : {}", exception.getMessage());
        model.addAttribute(ATTRIBUTE_NAME_FOR_ERROR_MESSAGE, exception.getMessage());
        return DEFAULT_ERROR_PAGE_URI;
    }

    @ExceptionHandler({
            FileStorageException.class,
            RuntimeException.class,
            EntityNotFoundException.class,
            MethodArgumentNotValidException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String defaultExceptionHandler(RuntimeException exception, Model model) {
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