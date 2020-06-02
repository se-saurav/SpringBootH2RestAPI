package com.coreide.services;

import com.coreide.exceptions.InvalidTransactionException;
import com.coreide.entities.Account;
import com.coreide.entities.Payment;

import java.util.List;

public interface AccountService {

    Account addAccount(Account account);

    Account findAccountByAccountNumber(Long accountNumber);

    List<Account> findAll();

    Account updateAccount(Account account);

    void deleteAccount(Long accountNumber);

    void makePayment(Payment payment) throws InvalidTransactionException;
}
