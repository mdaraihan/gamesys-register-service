package com.gamesys.api.register.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class PaymentCardBlockedException extends Exception {

    private static final long serialVersionUID = 1L;

    public PaymentCardBlockedException(String message) {
        super(message);
    }
}
