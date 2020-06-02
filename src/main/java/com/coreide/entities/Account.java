package com.coreide.entities;

import com.coreide.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue
    private Long accountNumber;
    private AccountType accountType;
    private Double balance;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Customer> customers = new ArrayList<>();

    public Account(){}

    public Account(AccountType accountType, Double balance) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
    }
}
