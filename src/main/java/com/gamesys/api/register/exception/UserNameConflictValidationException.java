package com.gamesys.api.register.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserNameConflictValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    public UserNameConflictValidationException(String message) {
        super(message);
    }
}
