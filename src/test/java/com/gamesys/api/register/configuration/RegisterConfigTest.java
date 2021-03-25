package com.gamesys.api.register.configuration;

import com.gamesys.api.register.domain.DomainRegisterFactory;
import com.gamesys.api.register.domain.validator.AgeValidator;
import com.gamesys.api.register.domain.validator.BlockedCardIssuerValidator;
import com.gamesys.api.register.domain.validator.CreateRegisterValidator;
import com.gamesys.api.register.domain.validator.DobValidator;
import com.gamesys.api.register.domain.validator.PasswordValidator;
import com.gamesys.api.register.domain.validator.PaymentCardNumberValidator;
import com.gamesys.api.register.domain.validator.UserNameValidator;
import com.gamesys.api.register.repository.RegisterRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RegisterConfigTest {

    private RegisterConfig registerConfig;
    @Mock
    private RegisterRepository registerRepository;
    @Mock
    private DomainRegisterFactory domainRegisterFactory;
    @Mock
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

    @Before
    public void setUp() {
        registerConfig = new RegisterConfig();
    }

    @Test
    public void registerService() {
        assertThat(registerConfig.registerService(registerRepository,
                domainRegisterFactory,
                createRegisterValidator)).isNotNull();
    }

    @Test
    public void createRegisterValidator() {
        assertThat(registerConfig.createRegisterValidator(userNameValidator,
                passwordValidator,
                dobValidator,
                paymentCardNumberValidator,
                ageValidator,
                blockedCardIssuerValidator)).isNotNull();
    }

    @Test
    public void userNameValidator() {
        assertThat(registerConfig.userNameValidator()).isNotNull();
    }

    @Test
    public void passwordValidator() {
        assertThat(registerConfig.passwordValidator()).isNotNull();
    }

    @Test
    public void dobValidator() {
        assertThat(registerConfig.dobValidator()).isNotNull();
    }

    @Test
    public void paymentCardNumberValidator() {
        assertThat(registerConfig.paymentCardNumberValidator()).isNotNull();
    }

    @Test
    public void ageValidator() {
        assertThat(registerConfig.ageValidator()).isNotNull();
    }

    @Test
    public void blockedCardIssuerValidator() {
        assertThat(registerConfig.blockedCardIssuerValidator(Collections.singletonList("111111")))
                .isNotNull();
    }

    @Test
    public void registerDtoFactory() {
        assertThat(registerConfig.registerDtoFactory()).isNotNull();
    }

    @Test
    public void domainRegisterFactory() {
        assertThat(registerConfig.domainRegisterFactory()).isNotNull();
    }
}