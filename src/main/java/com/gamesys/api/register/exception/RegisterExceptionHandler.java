package com.gamesys.api.register.exception;

import com.gamesys.api.register.domain.validator.RegisterValidationError;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static com.gamesys.api.register.domain.validator.RegisterValidationError.USER_NAME_EXISTS;

@ControllerAdvice
public class RegisterExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails.Builder()
                .withTimestamp(LocalDateTime.now())
                .withMessage(exception.getMessage())
                .withDetails(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BasicValidationException.class})
    public ResponseEntity<?> badRequestException(BasicValidationException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails.Builder()
                .withTimestamp(LocalDateTime.now())
                .withMessage(exception.getMessage())
                .withDetails(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AgeValidationException.class})
    public ResponseEntity<?> ageValidationException(AgeValidationException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails.Builder()
                .withTimestamp(LocalDateTime.now())
                .withMessage(exception.getMessage())
                .withDetails(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({PaymentCardBlockedException.class})
    public ResponseEntity<?> ageValidationException(PaymentCardBlockedException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails.Builder()
                .withTimestamp(LocalDateTime.now())
                .withMessage(exception.getMessage())
                .withDetails(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<?> userNameConflictException(WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails.Builder()
                .withTimestamp(LocalDateTime.now())
                .withMessage(Collections.singletonList(USER_NAME_EXISTS).toString())
                .withDetails(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails.Builder()
                .withTimestamp(LocalDateTime.now())
                .withMessage(exception.getMessage())
                .withDetails(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
