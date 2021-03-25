package com.gamesys.api.register.domain;

import com.gamesys.api.register.repository.model.Register;

public class DomainRegisterFactory {
    public Register createRegister(DomainRegister domainRegister) {
        return new Register(domainRegister.getUserName(),
                domainRegister.getPassword(),
                domainRegister.getDob(),
                domainRegister.getPaymentCardNumber());
    }

    public DomainRegister createDomainRegister(Register register) {
        return new DomainRegister.Builder()
                .withId(register.getId())
                .withUserName(register.getUserName())
                .withPassword(register.getPassword())
                .withDob(register.getDob())
                .withPaymentCardNumber(register.getPaymentCardNumber())
                .build();
    }
}
