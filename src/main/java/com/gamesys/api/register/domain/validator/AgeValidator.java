package com.gamesys.api.register.domain.validator;

import com.gamesys.api.register.domain.DomainRegister;

import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.gamesys.api.register.domain.validator.RegisterValidationError.AGE_UNDER_18;
import static com.gamesys.api.register.domain.validator.RegisterValidationError.DOB_INVALID;

public class AgeValidator implements RegisterValidator {

    private static final int MIN_AGE = 18;

    @Override
    public RegisterValidationResult validate(DomainRegister domainRegister) {
        try {
            LocalDate dob = LocalDate.parse(domainRegister.getDob());
            return getAgeValidationResult(dob);
        } catch (DateTimeParseException e) {
            return buildErrorResult(DOB_INVALID);
        }
    }

    private RegisterValidationResult getAgeValidationResult(LocalDate dob) {
        return hasAgeRequirement(dob)
                ? buildSuccessResult()
                : buildErrorResult(AGE_UNDER_18);
    }

    public boolean hasAgeRequirement(LocalDate dob) {
        return Period.between(dob, LocalDate.now()).getYears() >= MIN_AGE;
    }
}
