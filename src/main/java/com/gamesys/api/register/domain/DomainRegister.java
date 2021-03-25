package com.gamesys.api.register.domain;

public class DomainRegister {
    private final long id;
    private final String userName;
    private final String password;
    private final String dob;
    private final String paymentCardNumber;

    private DomainRegister(long id,
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

    public static class Builder {
        private long id;
        private String userName;
        private String password;
        private String dob;
        private String paymentCardNumber;

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withDob(String dob) {
            this.dob = dob;
            return this;
        }

        public Builder withPaymentCardNumber(String paymentCardNumber) {
            this.paymentCardNumber = paymentCardNumber;
            return this;
        }

        public DomainRegister build() {
            return new DomainRegister(id, userName, password, dob, paymentCardNumber);
        }

    }
}
