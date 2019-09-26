package com.example.myapp.controllers.exception;

import com.example.myapp.models.ApiError;
import com.mongodb.lang.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<ApiError> handleExceptionInternal(Exception exception,
                                                             @Nullable ApiError body,
                                                             HttpHeaders headers, HttpStatus status,
                                                             WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, exception, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }

    private ResponseEntity<ApiError> handlePersonNotFoundException(PersonNotFoundException exception,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        List<String> errors = Collections.singletonList(exception.getMessage());
        return handleExceptionInternal(exception, new ApiError(errors), headers, status, request);
    }

    @ExceptionHandler({
            PersonNotFoundException.class,
            CustomerNotFoundException.class,
            ParseException.class,
            NumberFormatException.class
    })
    @Nullable
    public final ResponseEntity<ApiError> handleExceptions(Exception exception,
                                                          WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        LOGGER.error("Handling " + exception.getClass().getSimpleName() + " due to " + exception.getMessage());

        if (exception instanceof PersonNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            PersonNotFoundException pnf = (PersonNotFoundException) exception;
            return handleException(pnf, headers, status, request);
        }
        if (exception instanceof CustomerNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            CustomerNotFoundException cnf = (CustomerNotFoundException) exception;
            return handleException(cnf, headers, status, request);
        }
        if (exception instanceof ParseException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            ParseException parseException = (ParseException) exception;
            return handleException(parseException, headers, status, request);
        }
        if (exception instanceof NumberFormatException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            NumberFormatException numberFormatException = (NumberFormatException) exception;
            return handleException(numberFormatException, headers, status, request);
        }
        if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Unknown exception type: " + exception.getClass().getName());
            }

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(exception, null, headers, status, request);
    }

    private ResponseEntity<ApiError> handleException(Exception exception,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        List<String> errors = Collections.singletonList(exception.getMessage());
        return handleExceptionInternal(exception, new ApiError(errors), headers, status, request);
    }

}
