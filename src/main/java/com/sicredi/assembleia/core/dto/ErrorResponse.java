package com.sicredi.assembleia.core.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        String name,
        String message,
        HttpStatus httpStatus
) {
}
