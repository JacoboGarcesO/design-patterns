package com.example.patterns_banking.models;

import com.example.patterns_banking.utils.AccountType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String number;

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType type;

    private BigDecimal balance;

    @Column(name = "is_active")
    private Boolean isActive = true;

    private Card(Builder builder){
        this.id = builder.id;
        this.number = builder.number;
        this.type = builder.type;
        this.balance = builder.balance;
        this.isActive = builder.isActive;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private Long id;
        private String number;
        private AccountType type;
        private BigDecimal balance;
        private Boolean isActive = true;

        private Builder(){}

        public Builder id(Long id){
            this.id=id;
            return this;
        }

        public Builder number(String number){
            this.number=number;
            return this;
        }

        public Builder accountType(AccountType type){
            this.type=type;
            return this;
        }

        public Builder balance(BigDecimal balance){
            this.balance=balance;
            return this;
        }

        public Builder isActive(Boolean isActive){
            this.isActive=isActive;
            return this;
        }

        public Card build() {
            return new Card(this);
        }
    }
}
