package com.gamesys.api.register.domain.validator;

import com.gamesys.api.register.domain.validator.RegisterValidationResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static com.gamesys.api.register.domain.validator.RegisterValidationError.PASSWORD_INVALID;
import static com.gamesys.api.register.domain.validator.RegisterValidationError.USER_NAME_INVALID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterValidationResultTest {

    private RegisterValidationResult registerValidationResult;

    @Mock
    private RegisterValidationResult userNameValidationResult;

    @Mock
    private RegisterValidationResult passwordValidationResult;

    @Test
    public void combine_withValidationErrors() {
        when(userNameValidationResult.getErrors()).thenReturn(Collections.singleton(USER_NAME_INVALID));
        when(passwordValidationResult.getErrors()).thenReturn(Collections.singleton(PASSWORD_INVALID));

        RegisterValidationResult result = RegisterValidationResult.combine(userNameValidationResult,
                passwordValidationResult);

        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getErrors()).contains(USER_NAME_INVALID, PASSWORD_INVALID);
    }

    @Test
    public void combine_withNoValidationErrors() {
        when(userNameValidationResult.isSuccessful()).thenReturn(true);
        when(passwordValidationResult.isSuccessful()).thenReturn(true);

        RegisterValidationResult result = RegisterValidationResult.combine(userNameValidationResult,
                passwordValidationResult);

        assertThat(result.isSuccessful()).isTrue();
        assertThat(result.getErrors()).isEmpty();
    }

    @Test
    public void isSuccessful_withError() {
        registerValidationResult = new RegisterValidationResult(null);

        assertThat(registerValidationResult.isSuccessful()).isTrue();
    }

    @Test
    public void isSuccessful_withoutError() {
        registerValidationResult = new RegisterValidationResult(Collections.singleton(USER_NAME_INVALID));

        assertThat(registerValidationResult.isSuccessful()).isFalse();
        assertThat(registerValidationResult.getErrors()).containsExactly(USER_NAME_INVALID);
    }

    @Test
    public void getErrors() {
        registerValidationResult = new RegisterValidationResult(Collections.singleton(USER_NAME_INVALID));

        assertThat(registerValidationResult.getErrors()).containsExactly(USER_NAME_INVALID);
    }
}