package com.sicredi.assembleia.core.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends MainHttpException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}