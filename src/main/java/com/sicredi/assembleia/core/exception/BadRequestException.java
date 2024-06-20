package com.sicredi.assembleia.core.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends MainHttpException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}