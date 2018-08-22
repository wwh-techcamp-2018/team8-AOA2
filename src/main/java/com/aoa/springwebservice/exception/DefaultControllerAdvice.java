package com.aoa.springwebservice.exception;

import com.aoa.springwebservice.response.ApiError;
import com.aoa.springwebservice.response.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@ControllerAdvice//(basePackages = {"/web"})
@Slf4j
public class DefaultControllerAdvice{

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException exception) {
        log.debug("handleRuntimeException {} ", exception.getMessage());
        return "/error";
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    protected ResponseEntity<ValidationErrorResponse> handleBindException2(BindException ex, HttpServletRequest request,
                                                                 HttpServletResponse response, @Nullable Object handler) throws IOException {
        log.debug("handleBindException 2");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        log.debug("handler {}",handler.getClass());
        return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body(
                new ValidationErrorResponse(new ApiError("ERROR MSG", ex.getLocalizedMessage()))
                .addAllErrors(ex.getBindingResult())
        );
    }
}
