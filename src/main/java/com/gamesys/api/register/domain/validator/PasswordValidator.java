package com.gamesys.api.register.domain.validator;

import com.gamesys.api.register.domain.DomainRegister;

import java.util.Optional;
import java.util.regex.Pattern;

import static com.gamesys.api.register.domain.validator.RegisterValidationError.PASSWORD_INVALID;

public class PasswordValidator implements RegisterValidator {

    private static final Integer MINIMUM_LENGTH = 4;
    private static final Pattern UPPER_CASE_PATTEN = Pattern.compile("[A-Z]");
    private static final Pattern DIGIT_PATTEN = Pattern.compile("[0-9]");

    @Override
    public RegisterValidationResult validate(DomainRegister domainRegister) {
        return isValid(domainRegister.getPassword())
                ? buildSuccessResult()
                : buildErrorResult(PASSWORD_INVALID);
    }

    private boolean isValid(String password) {
        return isValidLength(password)
                && hasUpperCase(password)
                && hasDigit(password);
    }

    private boolean isValidLength(String password) {
        return Optional.ofNullable(password)
                .map(p -> p.length() >= MINIMUM_LENGTH)
                .orElse(false);
    }

    private boolean hasUpperCase(String password) {
        return UPPER_CASE_PATTEN.matcher(password).find();
    }

    private boolean hasDigit(String password) {
        return DIGIT_PATTEN.matcher(password).find();
    }

}
