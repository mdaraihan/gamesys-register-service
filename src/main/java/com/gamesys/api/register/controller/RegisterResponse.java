package com.gamesys.api.register.controller;

import java.time.LocalDate;

public class RegisterResponse {
    private long id;
    private String userName;
    private String password;
    private String dob;
    private String paymentCardNumber;

    public RegisterResponse(long id,
                            String userName,
                            String password,
                            String dob,
                            String paymentCardNumber) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.dob = dob;
        this.paymentCardNumber = paymentCardNumber;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getDob() {
        return dob;
    }

    public String getPaymentCardNumber() {
        return paymentCardNumber;
    }
}
