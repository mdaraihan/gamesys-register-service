package com.gamesys.api.register.domain;

import com.gamesys.api.register.repository.model.Register;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DomainRegisterFactoryTest {

    private static final String USER_NAME_1 = "USER_NAME_1";
    private static final String PASSWORD_1 = "PASSWORD_1";
    private static final String DOB_1 = "DOB_1";
    private static final String CARD_NUMBER_1 = "CARD_NUMBER_1";

    private DomainRegisterFactory domainRegisterFactory;

    @Before
    public void setUp() {
        domainRegisterFactory = new DomainRegisterFactory();
    }

    @Test
    public void createRegister() {
        DomainRegister domainRegister = new DomainRegister.Builder()
                .withUserName(USER_NAME_1)
                .withPassword(PASSWORD_1)
                .withDob(DOB_1)
                .withPaymentCardNumber(CARD_NUMBER_1)
                .build();

        Register register = domainRegisterFactory.createRegister(domainRegister);

        assertThat(register).extracting(Register::getUserName,
                Register::getPassword,
                Register::getDob,
                Register::getPaymentCardNumber)
                .containsExactly(domainRegister.getUserName(),
                        domainRegister.getPassword(),
                        domainRegister.getDob(),
                        domainRegister.getPaymentCardNumber());
    }

    @Test
    public void createDomainRegister() {
        Register register = new Register(USER_NAME_1, PASSWORD_1, DOB_1, CARD_NUMBER_1);

        DomainRegister domainRegister = domainRegisterFactory.createDomainRegister(register);

        assertThat(domainRegister).extracting(DomainRegister::getUserName,
                DomainRegister::getPassword,
                DomainRegister::getDob,
                DomainRegister::getPaymentCardNumber)
                .containsExactly(register.getUserName(),
                        register.getPassword(),
                        register.getDob(),
                        register.getPaymentCardNumber());
    }
}