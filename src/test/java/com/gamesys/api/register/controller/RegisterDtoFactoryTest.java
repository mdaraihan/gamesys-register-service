package com.gamesys.api.register.controller;

import com.gamesys.api.register.domain.DomainRegister;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterDtoFactoryTest {

    private static final String USER_NAME_1 = "USER_NAME_1";
    private static final String PASSWORD_1 = "PASSWORD_1";
    private static final String DOB_1 = "DOB_1";
    private static final String CARD_NUMBER_1 = "CARD_NUMBER_1";

    private RegisterDtoFactory registerDtoFactory;

    @Before
    public void setUp() {
        registerDtoFactory = new RegisterDtoFactory();
    }

    @Test
    public void createDomainRegister() {
        RegisterRequest registerRequest = new RegisterRequest.Builder()
                .withUserName(USER_NAME_1)
                .withPassword(PASSWORD_1)
                .withDob(DOB_1)
                .withPaymentCardNumber(CARD_NUMBER_1)
                .build();

        DomainRegister domainRegister = registerDtoFactory.createDomainRegister(registerRequest);

        assertThat(domainRegister).extracting(DomainRegister::getUserName,
                DomainRegister::getPassword,
                DomainRegister::getDob,
                DomainRegister::getPaymentCardNumber)
                .containsExactly(registerRequest.getUserName(),
                        registerRequest.getPassword(),
                        registerRequest.getDob(),
                        registerRequest.getPaymentCardNumber());
    }

    @Test
    public void createRegisterResponse() {

        DomainRegister domainRegister = new DomainRegister.Builder()
                .withUserName(USER_NAME_1)
                .withPassword(PASSWORD_1)
                .withDob(DOB_1)
                .withPaymentCardNumber(CARD_NUMBER_1)
                .build();

        RegisterResponse registerResponse = registerDtoFactory.createRegisterResponse(domainRegister);

        assertThat(registerResponse).extracting(RegisterResponse::getUserName,
                RegisterResponse::getPassword,
                RegisterResponse::getDob,
                RegisterResponse::getPaymentCardNumber)
                .containsExactly(domainRegister.getUserName(),
                        domainRegister.getPassword(),
                        domainRegister.getDob(),
                        domainRegister.getPaymentCardNumber());
    }
}