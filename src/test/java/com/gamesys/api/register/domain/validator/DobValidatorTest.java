package com.gamesys.api.register.domain.validator;

import com.gamesys.api.register.domain.DomainRegister;
import com.gamesys.api.register.domain.validator.DobValidator;
import com.gamesys.api.register.domain.validator.RegisterValidationResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.gamesys.api.register.domain.validator.RegisterValidationError.DOB_INVALID;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DobValidatorTest {

    private DobValidator dobValidator;
    @Mock
    private DomainRegister domainRegister;

    @Before
    public void setUp() {
        dobValidator = new DobValidator(ISO_LOCAL_DATE);
    }

    @Test
    public void validate_withInvalidDobFormat() {
        when(domainRegister.getDob()).thenReturn("20110101");

        RegisterValidationResult result = dobValidator.validate(domainRegister);

        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getErrors()).containsExactly(DOB_INVALID);
    }

    @Test
    public void validate_withValidDobFormat() {
        when(domainRegister.getDob()).thenReturn("2011-01-01");

        RegisterValidationResult result = dobValidator.validate(domainRegister);

        assertThat(result.isSuccessful()).isTrue();
        assertThat(result.getErrors()).isEmpty();
    }
}