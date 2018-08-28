package com.aoa.springwebservice.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;


@Getter @Setter @NoArgsConstructor @ToString

public class ValidationErrorResponse{
    @JsonUnwrapped
    private ApiError apiError;
    private List<ValidationError> errors = new ArrayList<>();
    //todo MessageSource 추가

    public ValidationErrorResponse(ApiError apiError) {
        this.apiError = apiError;
    }

    public ValidationErrorResponse addError(ValidationError error) {
        this.errors.add(error);
        return this;
    }
    public ValidationErrorResponse addAllErrors(BindingResult bindingResult) {
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            ValidationError error = new ValidationError("field "+ fieldError.getField(), "default "+fieldError.getDefaultMessage());
            addError(error);
        }
        return this;
    }
    @ToString @Getter @NoArgsConstructor
    protected class ValidationError {

        private String fieldName;
        private String errorMessage;

        public ValidationError(String fieldName, String errorMessage) {
            this.fieldName = fieldName;
            this.errorMessage = errorMessage;
        }

    }
}
