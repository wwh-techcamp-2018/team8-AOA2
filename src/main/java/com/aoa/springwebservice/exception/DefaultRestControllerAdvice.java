package com.aoa.springwebservice.exception;

import com.aoa.springwebservice.response.ApiError;
import com.aoa.springwebservice.response.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.BindException;

@RestControllerAdvice
@RequestMapping("/api")
@Slf4j
public class DefaultRestControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String defaultExceptionHandler(RuntimeException exception) {
        exception.printStackTrace();
        return exception.getMessage();
    }

    @ExceptionHandler(FileStorageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String fileStorageExceptionHandler(FileStorageException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler({org.springframework.validation.BindException.class, MethodArgumentNotValidException.class})
    protected ResponseEntity<ValidationErrorResponse> handleBindException2(org.springframework.validation.BindException ex, HttpServletRequest request,
                                                                 HttpServletResponse response, @Nullable Object handler) throws IOException {
        log.debug("handleBindException 2");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        log.debug("handler {}",handler.getClass());
        log.debug("ReseonseEntity {}", ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body(
                new ValidationErrorResponse(new ApiError("ERROR MSG", ex.getLocalizedMessage()))
                        .addAllErrors(ex.getBindingResult())));
        return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body(
                new ValidationErrorResponse(new ApiError("ERROR MSG", ex.getLocalizedMessage()))
                        .addAllErrors(ex.getBindingResult())
        );
    }
}