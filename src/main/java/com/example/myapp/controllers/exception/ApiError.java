package com.example.myapp.controllers.exception;

import java.util.List;

public class ApiError {

    private List<String> errors ;

    protected ApiError(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
