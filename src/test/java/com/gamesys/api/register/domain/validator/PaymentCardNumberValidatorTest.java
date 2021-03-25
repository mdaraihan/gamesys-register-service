package com.gamesys.api.register.domain.validator;

import com.gamesys.api.register.domain.DomainRegister;
import com.gamesys.api.register.domain.validator.PaymentCardNumberValidator;
import com.gamesys.api.register.domain.validator.RegisterValidationResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.gamesys.api.register.domain.validator.RegisterValidationError.PAYMENT_CARD_NUMBER_INVALID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentCardNumberValidatorTest {

    private PaymentCardNumberValidator paymentCardNumberValidator;

    @Mock
    private DomainRegister domainRegister;

    @Before
    public void setUp() {
        paymentCardNumberValidator = new PaymentCardNumberValidator();
    }

    @Test
    public void validate_withNullPaymentCardNumber() {
        when(domainRegister.getPaymentCardNumber()).thenReturn(null);

        RegisterValidationResult result = paymentCardNumberValidator.validate(domainRegister);

        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getErrors()).containsExactly(PAYMENT_CARD_NUMBER_INVALID);
    }

    @Test
    public void validate_withLengthLessThanMin() {
        when(domainRegister.getPaymentCardNumber()).thenReturn("34929308105442");

        RegisterValidationResult result = paymentCardNumberValidator.validate(domainRegister);

        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getErrors()).containsExactly(PAYMENT_CARD_NUMBER_INVALID);
    }

    @Test
    public void validate_withLengthGreaterThanMax() {
        when(domainRegister.getPaymentCardNumber()).thenReturn("34929308105442212431");

        RegisterValidationResult result = paymentCardNumberValidator.validate(domainRegister);

        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getErrors()).containsExactly(PAYMENT_CARD_NUMBER_INVALID);
    }

    @Test
    public void validate_withValidPaymentCardNumber() {
        when(domainRegister.getPaymentCardNumber()).thenReturn("349293081054422");

        RegisterValidationResult result = paymentCardNumberValidator.validate(domainRegister);

        assertThat(result.isSuccessful()).isTrue();
        assertThat(result.getErrors()).isEmpty();
    }
}