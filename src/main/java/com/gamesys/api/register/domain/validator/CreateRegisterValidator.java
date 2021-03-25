package com.gamesys.api.register.domain.validator;

import com.gamesys.api.register.domain.DomainRegister;

public class CreateRegisterValidator {
    private final UserNameValidator userNameValidator;
    private final PasswordValidator passwordValidator;
    private final DobValidator dobValidator;
    private final PaymentCardNumberValidator paymentCardNumberValidator;
    private final AgeValidator ageValidator;
    private final BlockedCardIssuerValidator blockedCardIssuerValidator;

    public CreateRegisterValidator(UserNameValidator userNameValidator,
                                   PasswordValidator passwordValidator,
                                   DobValidator dobValidator,
                                   PaymentCardNumberValidator paymentCardNumberValidator,
                                   AgeValidator ageValidator,
                                   BlockedCardIssuerValidator blockedCardIssuerValidator) {
        this.userNameValidator = userNameValidator;
        this.passwordValidator = passwordValidator;
        this.dobValidator = dobValidator;
        this.paymentCardNumberValidator = paymentCardNumberValidator;
        this.ageValidator = ageValidator;
        this.blockedCardIssuerValidator = blockedCardIssuerValidator;
    }

    public RegisterValidationResult validate(DomainRegister domainRegister) {
        return RegisterValidationResult.combine(userNameValidator.validate(domainRegister),
                passwordValidator.validate(domainRegister),
                dobValidator.validate(domainRegister),
                paymentCardNumberValidator.validate(domainRegister),
                ageValidator.validate(domainRegister),
                blockedCardIssuerValidator.validate(domainRegister));

    }
}
