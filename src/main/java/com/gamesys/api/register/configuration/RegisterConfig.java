package com.gamesys.api.register.configuration;

import com.gamesys.api.register.controller.RegisterDtoFactory;
import com.gamesys.api.register.domain.validator.AgeValidator;
import com.gamesys.api.register.domain.validator.BlockedCardIssuerValidator;
import com.gamesys.api.register.domain.validator.CreateRegisterValidator;
import com.gamesys.api.register.domain.validator.DobValidator;
import com.gamesys.api.register.domain.DomainRegisterFactory;
import com.gamesys.api.register.domain.validator.PasswordValidator;
import com.gamesys.api.register.domain.validator.PaymentCardNumberValidator;
import com.gamesys.api.register.domain.RegisterService;
import com.gamesys.api.register.domain.validator.UserNameValidator;
import com.gamesys.api.register.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
public class RegisterConfig {

    @Bean
    public RegisterService registerService(RegisterRepository registerRepository,
                                           DomainRegisterFactory domainRegisterFactory,
                                           CreateRegisterValidator validator) {
        return new RegisterService(registerRepository, domainRegisterFactory, validator);
    }

    @Bean
    public CreateRegisterValidator createRegisterValidator(UserNameValidator userNameValidator,
                                                           PasswordValidator passwordValidator,
                                                           DobValidator dobValidator,
                                                           PaymentCardNumberValidator paymentCardNumberValidator,
                                                           AgeValidator ageValidator,
                                                           BlockedCardIssuerValidator blockedCardIssuerValidator) {
        return new CreateRegisterValidator(userNameValidator,
                passwordValidator,
                dobValidator,
                paymentCardNumberValidator,
                ageValidator,
                blockedCardIssuerValidator);
    }

    @Bean
    public UserNameValidator userNameValidator() {
        return new UserNameValidator();
    }

    @Bean
    public PasswordValidator passwordValidator() {
        return new PasswordValidator();
    }

    @Bean
    public DobValidator dobValidator() {
        return new DobValidator(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Bean
    public PaymentCardNumberValidator paymentCardNumberValidator() {
        return new PaymentCardNumberValidator();
    }

    @Bean
    public AgeValidator ageValidator() {
        return new AgeValidator();
    }

    @Bean
    BlockedCardIssuerValidator blockedCardIssuerValidator(@Value("#{'${blocked.payment.iin}'.split(',')}") List<String> blockedPaymentIin) {
        return new BlockedCardIssuerValidator(blockedPaymentIin);
    }

    @Bean
    public RegisterDtoFactory registerDtoFactory() {
        return new RegisterDtoFactory();
    }

    @Bean
    public DomainRegisterFactory domainRegisterFactory() {
        return new DomainRegisterFactory();
    }
}
