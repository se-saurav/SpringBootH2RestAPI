package com.coreide.entities;

import lombok.Data;

@Data
public class Payment {
    private Long fromAccount;
    private Long toAccount;
    private Double amount;

    public Payment(){}

    public Payment(Long fromAccount, Long toAccount, Double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }
}
