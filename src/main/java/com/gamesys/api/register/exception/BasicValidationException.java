package com.gamesys.api.register.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BasicValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    public BasicValidationException(String message) {
        super(message);
    }
}
