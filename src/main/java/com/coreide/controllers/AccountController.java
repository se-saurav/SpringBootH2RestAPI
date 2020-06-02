package com.coreide.controllers;

import com.coreide.exceptions.InvalidTransactionException;
import com.coreide.entities.Payment;
import com.coreide.exceptions.ResourceNotFoundException;
import com.coreide.entities.Account;
import com.coreide.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @ResponseBody
    @PostMapping
    public ResponseEntity<Account> addAccount(@RequestBody Account account){
        account = accountService.addAccount(account);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @ResponseBody
    @GetMapping(value = "/{accountNumber}")
    public Account getAccount(@PathVariable(value = "accountNumber", required = true) Long accountNumber){
        Account account = accountService.findAccountByAccountNumber(accountNumber);
        if(account == null){
            throw new ResourceNotFoundException("Account doesn't exists");
        }
        return account;
    }

    @ResponseBody
    @GetMapping
    public List<Account> getAccounts(){
        List<Account> accounts = accountService.findAll();
        if(accounts == null || accounts.isEmpty()){
            throw new ResourceNotFoundException("Account doesn't exists");
        }
        return accounts;
    }

    @ResponseBody
    @PutMapping(value = "/{accountNumber}")
    public Account updateAccount(@PathVariable(value = "accountNumber", required = true) Long accountNumber, @RequestBody Account account){
        account.setAccountNumber(accountNumber);
        Account accountUpdated = accountService.updateAccount(account);
        if(accountUpdated == null){
            throw new ResourceNotFoundException("Account doesn't exists");
        }
        return accountUpdated;
    }

    @DeleteMapping(value = "/{accountNumber}")
    public void deleteAccount(@PathVariable(value = "accountNumber", required = true) Long accountNumber){
        accountService.deleteAccount(accountNumber);
    }

    @PostMapping(value = "/transferFund")
    public void makePayment(@RequestBody Payment payment){
        try {
            accountService.makePayment(payment);
        }catch (InvalidTransactionException ex){
            throw ex;
        }catch (Exception ex){
            throw new InvalidTransactionException("Payment failed");
        }
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleSecurityException(ResourceNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(InvalidTransactionException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleSecurityException(InvalidTransactionException ex) {
        return ex.getMessage();
    }
}
