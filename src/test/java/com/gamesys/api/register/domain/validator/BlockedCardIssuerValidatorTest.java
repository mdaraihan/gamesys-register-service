package com.gamesys.api.register.domain.validator;

import com.gamesys.api.register.domain.DomainRegister;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static com.gamesys.api.register.domain.validator.RegisterValidationError.PAYMENT_CARD_ISSUER_BLOCKED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BlockedCardIssuerValidatorTest {

    private static final String BLOCKED_IIN_1 = "111111";
    private static final String BLOCKED_IIN_2 = "222222";
    private static final String BLOCKED_IIN_3 = "333333";
    private static final List<String> BLOCKED_CARDS = Arrays.asList(BLOCKED_IIN_1,
            BLOCKED_IIN_2,
            BLOCKED_IIN_3);

    private BlockedCardIssuerValidator blockedCardIssuerValidator;

    @Mock
    private DomainRegister domainRegister;

    @Before
    public void setUp() {
        blockedCardIssuerValidator = new BlockedCardIssuerValidator(BLOCKED_CARDS);
    }

    @Test
    public void validate_withBlockedCard() {
        when(domainRegister.getPaymentCardNumber()).thenReturn("111111354678987");

        RegisterValidationResult result = blockedCardIssuerValidator.validate(domainRegister);

        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getErrors()).containsExactly(PAYMENT_CARD_ISSUER_BLOCKED);
    }

    @Test
    public void validate_withValidCard() {
        when(domainRegister.getPaymentCardNumber()).thenReturn("45467891354678987");

        RegisterValidationResult result = blockedCardIssuerValidator.validate(domainRegister);

        assertThat(result.isSuccessful()).isTrue();
        assertThat(result.getErrors()).isEmpty();
    }
}