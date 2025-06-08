package com.rohlik.rohlik.controller.exception;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder()
@RequiredArgsConstructor
public class ErrorResponse {

    private final Long timestamp;
    private final HttpStatus status;
    private final String error;
    private final String message;
    private final String path;
}
