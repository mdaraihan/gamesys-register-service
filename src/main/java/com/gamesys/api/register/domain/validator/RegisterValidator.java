package com.gamesys.api.register.domain.validator;

import com.gamesys.api.register.domain.DomainRegister;

import java.util.Collections;
import java.util.Set;

import static java.util.Collections.singleton;

interface RegisterValidator {

    RegisterValidationResult validate(DomainRegister domainRegister);

    default RegisterValidationResult buildSuccessResult() {
        return buildErrorsResult(Collections.emptySet());
    }

    default RegisterValidationResult buildErrorResult(RegisterValidationError error) {
        return buildErrorsResult(singleton(error));
    }

    default RegisterValidationResult buildErrorsResult(Set<RegisterValidationError> errors) {
        return new RegisterValidationResult(errors);
    }
}
