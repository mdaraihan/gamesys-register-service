package com.gamesys.api.register.controller;

import com.gamesys.api.register.domain.DomainRegister;

public class RegisterDtoFactory {

    public DomainRegister createDomainRegister(RegisterRequest registerRequest) {
        return new DomainRegister.Builder().
                withUserName(registerRequest.getUserName())
                .withPassword(registerRequest.getPassword())
                .withDob(registerRequest.getDob())
                .withPaymentCardNumber(registerRequest.getPaymentCardNumber())
                .build();
    }

    public RegisterResponse createRegisterResponse(DomainRegister domainRegister) {
        return new RegisterResponse(domainRegister.getId(),
                domainRegister.getUserName(),
                domainRegister.getPassword(),
                domainRegister.getDob(),
                domainRegister.getPaymentCardNumber());
    }
}
