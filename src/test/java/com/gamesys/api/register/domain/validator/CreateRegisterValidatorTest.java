package com.gamesys.api.register.domain.validator;

import com.gamesys.api.register.domain.DomainRegister;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static com.gamesys.api.register.domain.validator.RegisterValidationError.AGE_UNDER_18;
import static com.gamesys.api.register.domain.validator.RegisterValidationError.DOB_INVALID;
import static com.gamesys.api.register.domain.validator.RegisterValidationError.PASSWORD_INVALID;
import static com.gamesys.api.register.domain.validator.RegisterValidationError.PAYMENT_CARD_ISSUER_BLOCKED;
import static com.gamesys.api.register.domain.validator.RegisterValidationError.PAYMENT_CARD_NUMBER_INVALID;
import static com.gamesys.api.register.domain.validator.RegisterValidationError.USER_NAME_INVALID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateRegisterValidatorTest {

    private CreateRegisterValidator createRegisterValidator;

    @Mock
    private UserNameValidator userNameValidator;
    @Mock
    private PasswordValidator passwordValidator;
    @Mock
    private DobValidator dobValidator;
    @Mock
    private PaymentCardNumberValidator paymentCardNumberValidator;
    @Mock
    private AgeValidator ageValidator;
    @Mock
    private BlockedCardIssuerValidator blockedCardIssuerValidator;
    @Mock
    private RegisterValidationResult validationResultInvalidUserName;
    @Mock
    private RegisterValidationResult validationResultInvalidPassword;
    @Mock
    private RegisterValidationResult validationResultInvalidDob;
    @Mock
    private RegisterValidationResult validationResultInvalidCardNumber;
    @Mock
    private RegisterValidationResult validationResultInvalidAge;
    @Mock
    private RegisterValidationResult validationResultBlockedCard;
    @Mock
    private RegisterValidationResult validationResultValidUserName;
    @Mock
    private RegisterValidationResult validationResultValidPassword;
    @Mock
    private RegisterValidationResult validationResultValidDob;
    @Mock
    private RegisterValidationResult validationResultValidCardNumber;
    @Mock
    private RegisterValidationResult validationResultValidAge;
    @Mock
    private DomainRegister domainRegister;

    @Before
    public void setUp() {
        createRegisterValidator = new CreateRegisterValidator(userNameValidator,
                passwordValidator,
                dobValidator,
                paymentCardNumberValidator,
                ageValidator,
                blockedCardIssuerValidator);
    }

    @Test
    public void validate_withUserNameAndPasswordInvalid() {

        when(userNameValidator.validate(domainRegister)).thenReturn(validationResultInvalidUserName);
        when(validationResultInvalidUserName.getErrors()).thenReturn(Collections.singleton(USER_NAME_INVALID));
        when(passwordValidator.validate(domainRegister)).thenReturn(validationResultInvalidPassword);
        when(validationResultInvalidPassword.getErrors()).thenReturn(Collections.singleton(PASSWORD_INVALID));
        when(dobValidator.validate(domainRegister)).thenReturn(validationResultInvalidDob);
        when(validationResultInvalidDob.getErrors()).thenReturn(Collections.singleton(DOB_INVALID));
        when(paymentCardNumberValidator.validate(domainRegister)).thenReturn(validationResultInvalidCardNumber);
        when(validationResultInvalidCardNumber.getErrors()).thenReturn(Collections.singleton(PAYMENT_CARD_NUMBER_INVALID));
        when(ageValidator.validate(domainRegister)).thenReturn(validationResultInvalidAge);
        when(validationResultInvalidAge.getErrors()).thenReturn(Collections.singleton(AGE_UNDER_18));
        when(blockedCardIssuerValidator.validate(domainRegister)).thenReturn(validationResultBlockedCard);
        when(validationResultBlockedCard.getErrors()).thenReturn(Collections.singleton(PAYMENT_CARD_ISSUER_BLOCKED));

        RegisterValidationResult result = createRegisterValidator.validate(domainRegister);

        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getErrors()).containsExactlyInAnyOrder(USER_NAME_INVALID,
                PASSWORD_INVALID,
                DOB_INVALID,
                PAYMENT_CARD_NUMBER_INVALID,
                AGE_UNDER_18,
                PAYMENT_CARD_ISSUER_BLOCKED);
    }

    @Test
    public void validate_withUserNameAndPasswordValid() {

        mockRegistrationValidationResult(userNameValidator.validate(domainRegister), validationResultValidUserName);
        mockRegistrationValidationResult(passwordValidator.validate(domainRegister), validationResultValidPassword);
        mockRegistrationValidationResult(dobValidator.validate(domainRegister), validationResultValidDob);
        mockRegistrationValidationResult(paymentCardNumberValidator.validate(domainRegister), validationResultValidCardNumber);
        mockRegistrationValidationResult(ageValidator.validate(domainRegister), validationResultValidAge);
        mockRegistrationValidationResult(blockedCardIssuerValidator.validate(domainRegister), validationResultValidCardNumber);

        RegisterValidationResult result = createRegisterValidator.validate(domainRegister);

        assertThat(result.isSuccessful()).isTrue();
        assertThat(result.getErrors()).isEmpty();
    }

    private void mockRegistrationValidationResult(RegisterValidationResult validate,
                                                  RegisterValidationResult registerValidationResult) {
        when(validate).thenReturn(registerValidationResult);
        when(registerValidationResult.isSuccessful()).thenReturn(true);
    }
}