package com.gamesys.api.register.domain.validator;

import com.gamesys.api.register.domain.DomainRegister;
import com.gamesys.api.register.domain.validator.RegisterValidationError;
import com.gamesys.api.register.domain.validator.RegisterValidationResult;
import com.gamesys.api.register.domain.validator.UserNameValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserNameValidatorTest {

    private static final String USER_NAME_TEMPLATE = "userName1%s";
    private static final String STRING_SPACE = " ";

    private UserNameValidator userNameValidator;

    @Mock
    private DomainRegister domainRegister;

    @Before
    public void setUp() {
        userNameValidator = new UserNameValidator();
    }

    @Test
    public void validate_withAlphaNumeric() {
        when(domainRegister.getUserName()).thenReturn(String.format(USER_NAME_TEMPLATE, StringUtils.EMPTY));

        RegisterValidationResult result = userNameValidator.validate(domainRegister);

        assertThat(result.isSuccessful()).isTrue();
    }

    @Test
    public void validate_withSpace() {
        when(domainRegister.getUserName()).thenReturn(String.format(USER_NAME_TEMPLATE, STRING_SPACE));

        RegisterValidationResult result = userNameValidator.validate(domainRegister);

        assertWithError(result);
    }

    @Test
    public void validate_withNonAlphaNumericCharacters() {
        String[] nonAlphaNumericChars = "! @ # & ( ) – [ { } ] : ; ' , ? / * ` ~ $ ^ + = < > “".split(" ");

        Arrays.asList(nonAlphaNumericChars).forEach(nonAlphaNumeric -> {
            when(domainRegister.getUserName()).thenReturn(String.format(USER_NAME_TEMPLATE, nonAlphaNumeric));

            RegisterValidationResult result = userNameValidator.validate(domainRegister);

            assertWithError(result);
        });
    }

    private void assertWithError(RegisterValidationResult result) {
        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getErrors())
                .containsExactly(RegisterValidationError.USER_NAME_INVALID);
    }
}