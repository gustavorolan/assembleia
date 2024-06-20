package com.sicredi.assembleia.rest.config;

import com.sicredi.assembleia.core.dto.ErrorResponse;
import com.sicredi.assembleia.core.exception.MainException;
import com.sicredi.assembleia.core.exception.MainHttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private static final String GENERIC_MESSAGE = "Internal Server Error";

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(MainHttpException.class)
    public ResponseEntity<ErrorResponse> mainHttpExceptionHandler(MainHttpException exception) {
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(toErrorResponse(exception), exception.getHttpStatus());
    }

    @ExceptionHandler({Throwable.class, RuntimeException.class, MainException.class})
    public ResponseEntity<ErrorResponse> throwableHandler(Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);
        return new ResponseEntity<>(toErrorResponse(throwable), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ErrorResponse>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();

        List<ErrorResponse> errorsResponse = errors.stream().map(error -> {

                    logger.error(error.getDefaultMessage(), error);
                    return new ErrorResponse(
                            error.getClass().getSimpleName(),
                            error.getDefaultMessage(),
                            HttpStatus.BAD_REQUEST
                    );
                }
        ).toList();

        return ResponseEntity.badRequest().body(errorsResponse);
    }

    private ErrorResponse toErrorResponse(MainHttpException exception) {
        return new ErrorResponse(
                exception.getClass().getSimpleName(),
                exception.getMessage() != null ? exception.getMessage() : GENERIC_MESSAGE,
                exception.getHttpStatus()
        );
    }

    private ErrorResponse toErrorResponse(Throwable throwable) {
        return new ErrorResponse(
                throwable.getClass().getSimpleName(),
                throwable.getMessage() != null ? throwable.getMessage() : GENERIC_MESSAGE,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}