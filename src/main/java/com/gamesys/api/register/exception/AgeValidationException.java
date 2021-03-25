package com.gamesys.api.register.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AgeValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    public AgeValidationException(String message) {
        super(message);
    }
}
