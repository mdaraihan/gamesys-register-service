package com.gamesys.api.register.domain.validator;

import com.gamesys.api.register.domain.DomainRegister;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.gamesys.api.register.domain.validator.RegisterValidationError.DOB_INVALID;

public class DobValidator implements RegisterValidator {

    private final DateTimeFormatter dateFormatter;

    public DobValidator(DateTimeFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    @Override
    public RegisterValidationResult validate(DomainRegister domainRegister) {
        try {
            LocalDate.parse(domainRegister.getDob(), this.dateFormatter);
            return buildSuccessResult();
        } catch (DateTimeParseException e) {
            return buildErrorResult(DOB_INVALID);
        }
    }
}
