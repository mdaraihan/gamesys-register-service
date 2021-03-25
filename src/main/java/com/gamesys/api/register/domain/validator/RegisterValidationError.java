package com.gamesys.api.register.domain.validator;

public enum RegisterValidationError {
    USER_NAME_INVALID,
    USER_NAME_EXISTS,
    AGE_UNDER_18,
    DOB_INVALID,
    PASSWORD_INVALID,
    PAYMENT_CARD_NUMBER_INVALID,
    PAYMENT_CARD_ISSUER_BLOCKED
}
