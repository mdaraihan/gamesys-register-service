package com.gamesys.api.register.domain.validator;

import com.gamesys.api.register.domain.DomainRegister;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static com.gamesys.api.register.domain.validator.RegisterValidationError.AGE_UNDER_18;
import static com.gamesys.api.register.domain.validator.RegisterValidationError.DOB_INVALID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AgeValidatorTest {

    private static final LocalDate NOW = LocalDate.now();
    private static final LocalDate DOB_EIGHTEEN_YEARS_BEHIND = NOW.minusYears(18);
    private static final LocalDate DOB_SEVENTEEN_YEARS_364_DAYS_BEHIND = NOW.minusYears(18).plusDays(1);

    private AgeValidator ageValidator;

    @Mock
    private DomainRegister domainRegister;

    @Before
    public void setUp() {
        ageValidator = new AgeValidator();
    }

    @Test
    public void validate_with18Years() {
        when(domainRegister.getDob()).thenReturn(DOB_EIGHTEEN_YEARS_BEHIND.toString());

        RegisterValidationResult result = ageValidator.validate(domainRegister);

        assertThat(result.isSuccessful()).isTrue();
        assertThat(result.getErrors()).isEmpty();
    }

    @Test
    public void validate_with17Years() {
        when(domainRegister.getDob()).thenReturn(DOB_SEVENTEEN_YEARS_364_DAYS_BEHIND.toString());

        RegisterValidationResult result = ageValidator.validate(domainRegister);

        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getErrors()).containsExactly(AGE_UNDER_18);
    }

    @Test
    public void validate_withInvalidYears() {
        when(domainRegister.getDob()).thenReturn("19880101");

        RegisterValidationResult result = ageValidator.validate(domainRegister);

        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getErrors()).containsExactly(DOB_INVALID);
    }
}