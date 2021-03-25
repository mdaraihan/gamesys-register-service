package com.gamesys.api.register.controller;

class RegisterRequest {
    private final String userName;
    private final String password;
    private final String dob;
    private final String paymentCardNumber;

    private RegisterRequest(String userName,
                            String password,
                            String dob,
                            String paymentCardNumber) {
        this.userName = userName;
        this.password = password;
        this.dob = dob;
        this.paymentCardNumber = paymentCardNumber;
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

    static class Builder {
        private String userName;
        private String password;
        private String dob;
        private String paymentCardNumber;

        RegisterRequest.Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        RegisterRequest.Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        RegisterRequest.Builder withDob(String dob) {
            this.dob = dob;
            return this;
        }

        RegisterRequest.Builder withPaymentCardNumber(String paymentCardNumber) {
            this.paymentCardNumber = paymentCardNumber;
            return this;
        }

        RegisterRequest build() {
            return new RegisterRequest(userName, password, dob, paymentCardNumber);
        }

    }
}
