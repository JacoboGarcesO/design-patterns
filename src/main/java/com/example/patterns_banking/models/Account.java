package com.example.patterns_banking.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String number;
//    @Enumerated(EnumType.STRING)
//    private AccountType type;
    private BigDecimal balance;
    private Boolean active = true;

    private Account(Builder builder) {
        this.id = builder.id;
        this.number = builder.number;
        this.balance = builder.balance;
        this.active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String number;
        private BigDecimal balance;
        private Boolean active;

        private Builder() {}

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public Builder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        public Account build() {

            return new Account(this);
        }
    }
}
