package com.coreide.services.impl;

import com.coreide.exceptions.InvalidTransactionException;
import com.coreide.entities.Account;
import com.coreide.entities.Payment;
import com.coreide.repositories.AccountRepository;
import com.coreide.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    
    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account addAccount(Account account){
        if(account != null){
            return accountRepository.save(account);
        }
        return null;
    }

    @Override
    public Account findAccountByAccountNumber(Long accountNumber){
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public List<Account> findAll(){
        return accountRepository.findAll();
    }

    @Override
    public Account updateAccount(Account account){
        Account exAccount = accountRepository.findByAccountNumber(account.getAccountNumber());
        exAccount.setAccountType(account.getAccountType());
        exAccount.setBalance(account.getBalance());
        return accountRepository.save(exAccount);
    }

    @Override
    public void deleteAccount(Long accountNumber){
        accountRepository.deleteByAccountNumber(accountNumber);
    }

    @Override
    @Transactional
    public void makePayment(Payment payment) throws InvalidTransactionException {
        if(payment == null || payment.getAmount() == null || payment.getFromAccount() == null || payment.getToAccount() == null){
            throw new InvalidTransactionException("Invalid request");
        }
        Account fromAccount = accountRepository.findByAccountNumber(payment.getFromAccount());
        Account toAccount = accountRepository.findByAccountNumber(payment.getToAccount());
        if(toAccount == null){
            throw new InvalidTransactionException("Invalid to account");
        }else if(fromAccount == null) {
            throw new InvalidTransactionException("Invalid from account");
        }else if(fromAccount.getBalance()<payment.getAmount()){
            throw new InvalidTransactionException("Insufficient funds");
        }else if(fromAccount.equals(toAccount)){
            throw new InvalidTransactionException("From and to accounts can not be same");
        }
        fromAccount.setBalance(fromAccount.getBalance() - payment.getAmount());
        toAccount.setBalance(toAccount.getBalance() + payment.getAmount());
        accountRepository.save(toAccount);
        accountRepository.save(fromAccount);
    }
}
