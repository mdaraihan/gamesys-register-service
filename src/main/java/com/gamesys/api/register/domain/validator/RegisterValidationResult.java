package com.gamesys.api.register.domain.validator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class RegisterValidationResult {

    private final Set<RegisterValidationError> errors;

    public RegisterValidationResult(Set<RegisterValidationError> errors) {
        this.errors = errors != null ? Collections.unmodifiableSet(errors) : Collections.emptySet();
    }

    static RegisterValidationResult combine(RegisterValidationResult... registerValidationResults) {

        Set<RegisterValidationError> errors = Arrays.stream(registerValidationResults)
                .filter(result -> !result.isSuccessful())
                .flatMap(registerValidationResult -> registerValidationResult.getErrors().stream())
                .collect(Collectors.toSet());

        return new RegisterValidationResult(errors);
    }

    public boolean isSuccessful() {
        return errors.isEmpty();
    }

    public Set<RegisterValidationError> getErrors() {
        return errors;
    }
}
