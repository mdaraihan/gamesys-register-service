package com.gamesys.api.register.domain.validator;

import com.gamesys.api.register.domain.DomainRegister;

import java.util.List;

import static com.gamesys.api.register.domain.validator.RegisterValidationError.PAYMENT_CARD_ISSUER_BLOCKED;

public class BlockedCardIssuerValidator implements RegisterValidator {

    private final List<String> blockedPaymentIin;

    public BlockedCardIssuerValidator(List<String> blockedPaymentIin) {
        this.blockedPaymentIin = blockedPaymentIin;
    }

    @Override
    public RegisterValidationResult validate(DomainRegister domainRegister) {
        return blockedPaymentIin.stream()
                .anyMatch(s -> domainRegister.getPaymentCardNumber().startsWith(s))
                ? buildErrorResult(PAYMENT_CARD_ISSUER_BLOCKED)
                : buildSuccessResult();
    }
}
