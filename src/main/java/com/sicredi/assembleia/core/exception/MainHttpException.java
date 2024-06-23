package com.sicredi.assembleia.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MainHttpException extends RuntimeException {
    private final HttpStatus httpStatus;

    public MainHttpException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
