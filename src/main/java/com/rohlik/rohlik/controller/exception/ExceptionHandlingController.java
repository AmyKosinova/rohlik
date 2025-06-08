package com.rohlik.rohlik.controller.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleException1(IllegalStateException exception, ServletWebRequest webRequest) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.builder()
                                .status(HttpStatus.BAD_REQUEST)
                                .error("Illegal state")
                                .message(exception.getLocalizedMessage())
                                .path(webRequest.getRequest().getRequestURI())
                                .build());
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(RuntimeException exception, ServletWebRequest webRequest) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.builder()
                                .status(HttpStatus.BAD_REQUEST)
                                .error("Unexpected error")
                                .message(exception.getLocalizedMessage())
                                .path(webRequest.getRequest().getRequestURI())
                                .build()
                );
    }


}
