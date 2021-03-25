package com.gamesys.api.register.domain.validator;

import com.gamesys.api.register.domain.DomainRegister;
import com.gamesys.api.register.domain.validator.PasswordValidator;
import com.gamesys.api.register.domain.validator.RegisterValidationResult;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.gamesys.api.register.domain.validator.RegisterValidationError.PASSWORD_INVALID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PasswordValidatorTest {

    private PasswordValidator passwordValidator;

    @Mock
    private DomainRegister domainRegister;

    @Before
    public void setUp() {
        passwordValidator = new PasswordValidator();
    }

    @Test
    public void validate_withValidLength_withBothCase_withDigit_returnsTrueAndNoError() {
        when(domainRegister.getPassword()).thenReturn("abcD1");

        RegisterValidationResult result = passwordValidator.validate(domainRegister);

        assertNoErrorResult(result);
    }

    @Test
    public void validate_withLength3_returnsFalseAndError() {
        when(domainRegister.getPassword()).thenReturn("abc");

        RegisterValidationResult result = passwordValidator.validate(domainRegister);

        assertErrorResult(result);
    }

    @Test
    public void validate_withLowerCaseLettersLength4_returnsFalseAndError() {
        when(domainRegister.getPassword()).thenReturn("abcd");

        RegisterValidationResult result = passwordValidator.validate(domainRegister);

        assertErrorResult(result);
    }

    @Test
    public void validate_withNull_returnsFalseAndError() {
        when(domainRegister.getPassword()).thenReturn(null);

        RegisterValidationResult result = passwordValidator.validate(domainRegister);

        assertErrorResult(result);
    }

    @Test
    public void validate_withEmpty_returnsFalseAndError() {
        when(domainRegister.getPassword()).thenReturn(StringUtils.EMPTY);

        RegisterValidationResult result = passwordValidator.validate(domainRegister);

        assertErrorResult(result);
    }

    @Test
    public void validate_with4Blanks_returnsFalseAndError() {
        when(domainRegister.getPassword()).thenReturn("    ");

        RegisterValidationResult result = passwordValidator.validate(domainRegister);

        assertErrorResult(result);
    }

    @Test
    public void validate_withValidLength_withBothCase_returnsFalseAndError() {
        when(domainRegister.getPassword()).thenReturn("abcD");

        RegisterValidationResult result = passwordValidator.validate(domainRegister);

        assertErrorResult(result);
    }

    @Test
    public void validate_withValidLength_withUpperCase_withDigit_returnsTrueAndNoError() {
        when(domainRegister.getPassword()).thenReturn("ABC1");

        RegisterValidationResult result = passwordValidator.validate(domainRegister);

        assertNoErrorResult(result);
    }

    private void assertErrorResult(RegisterValidationResult result) {
        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getErrors()).containsExactly(PASSWORD_INVALID);
    }

    private void assertNoErrorResult(RegisterValidationResult result) {
        assertThat(result.isSuccessful()).isTrue();
        assertThat(result.getErrors()).isEmpty();
    }
}