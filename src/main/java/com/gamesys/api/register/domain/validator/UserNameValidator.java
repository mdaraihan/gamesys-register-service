package com.gamesys.api.register.domain.validator;

import com.gamesys.api.register.domain.DomainRegister;
import org.apache.commons.lang3.StringUtils;

import static com.gamesys.api.register.domain.validator.RegisterValidationError.USER_NAME_INVALID;

public class UserNameValidator implements RegisterValidator {

    @Override
    public RegisterValidationResult validate(DomainRegister domainRegister) {
        return StringUtils.isAlphanumeric(domainRegister.getUserName())
                ? buildSuccessResult()
                : buildErrorResult(USER_NAME_INVALID);
    }
}
