package edu.java.scrapper.controllers;

import edu.java.scrapper.controllers.model.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        ApiErrorResponse response = new ApiErrorResponse();

        if (e instanceof ErrorResponse errorResponse) {
            fillResponseFromErrorResponse(response, errorResponse);
        }
        if (e instanceof HttpStatusCodeException httpStatusCodeException) {
            fillResponseFromHttpStatusCodeException(response, httpStatusCodeException);
        }
        fillResponseFromException(response, e);

        HttpStatus responseCode;
        try {
            responseCode = HttpStatus.valueOf(Integer.parseInt(response.getCode()));
        } catch (IllegalArgumentException exception) {
            responseCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(response, responseCode);
    }

    private void fillResponseFromHttpStatusCodeException(ApiErrorResponse response, HttpStatusCodeException e) {
        if (response.getCode() == null) {
            response.setCode(Integer.toString(e.getStatusCode().value()));
        }
    }

    public static void fillResponseFromErrorResponse(ApiErrorResponse response, ErrorResponse e) {
        if (response.getCode() == null) {
            response.setCode(Integer.toString(e.getBody().getStatus()));
        }
        if (response.getDescription() == null) {
            response.setDescription(e.getBody().getDetail());
        }
    }

    public static void fillResponseFromException(ApiErrorResponse response, Exception e) {
        if (response.getCode() == null) {
            if (e instanceof HttpMessageNotReadableException) {
                response.setCode("400");
            } else {
                response.setCode("500");
            }
        }
        if (response.getExceptionName() == null) {
            response.setExceptionName(e.getClass().getSimpleName());
        }
        if (response.getExceptionMessage() == null) {
            response.setExceptionMessage(e.getMessage());
        }
        if (response.getDescription() == null) {
            response.setDescription(e.toString());
        }
        if (response.getStacktrace() == null) {
            response.setStacktrace(
                Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toList()
            );
        }
    }
}
