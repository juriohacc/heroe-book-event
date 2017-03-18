package fr.jh.common.controller;

import java.util.ArrayList;
import java.util.List;


public class ValidationErrorDto {

    private List<FieldErrorDTO> fieldErrors = new ArrayList<FieldErrorDTO>();

    public ValidationErrorDto() {

    }

    public void addFieldError(String path, String message) {
        FieldErrorDTO fieldError = new FieldErrorDTO(path, message);
        fieldErrors.add(fieldError);
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }
}