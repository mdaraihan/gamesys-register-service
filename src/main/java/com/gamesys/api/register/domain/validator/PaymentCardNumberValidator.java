package com.gamesys.api.register.domain.validator;

import com.gamesys.api.register.domain.DomainRegister;

import static com.gamesys.api.register.domain.validator.RegisterValidationError.PAYMENT_CARD_NUMBER_INVALID;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class PaymentCardNumberValidator implements RegisterValidator {

    private static final int MIN_LENGTH = 15;
    private static final int MAX_LENGTH = 19;

    @Override
    public RegisterValidationResult validate(DomainRegister domainRegister) {
        String paymentCardNumber = domainRegister.getPaymentCardNumber();
        return isNumeric(paymentCardNumber) && isLengthValid(paymentCardNumber)
                ? buildSuccessResult()
                : buildErrorResult(PAYMENT_CARD_NUMBER_INVALID);
    }

    private boolean isLengthValid(String paymentCardNumber) {

        int length = paymentCardNumber.length();

        return length >= MIN_LENGTH && length <= MAX_LENGTH;
    }
}
