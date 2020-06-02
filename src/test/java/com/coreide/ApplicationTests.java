package com.coreide;

import com.coreide.services.AccountService;
import com.coreide.entities.Account;
import com.coreide.entities.Customer;
import com.coreide.entities.Payment;
import com.coreide.enums.AccountType;
import com.coreide.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class ApplicationTests {

	@Autowired
	CustomerService customerService;

	@Autowired
    AccountService accountService;

	@Test
	void testPayment(){
		Account fromAccount = new Account(AccountType.Savings, 100d);
		Account toAccount = new Account(AccountType.Savings, 100d);
		fromAccount = accountService.addAccount(fromAccount);
		toAccount = accountService.addAccount(toAccount);

		Payment payment = new Payment(fromAccount.getAccountNumber(), toAccount.getAccountNumber(), 10d);
		accountService.makePayment(payment);

		fromAccount = accountService.findAccountByAccountNumber(fromAccount.getAccountNumber());
		toAccount = accountService.findAccountByAccountNumber(toAccount.getAccountNumber());
		Assert.isTrue(fromAccount.getBalance().equals(90d), "Transaction failed");
		Assert.isTrue(toAccount.getBalance().equals(110d), "Transaction failed");
	}

	@Test
	void createAccount(){
		Account account = new Account(AccountType.Savings, 100d);
		account = accountService.addAccount(account);
		account = accountService.findAccountByAccountNumber(account.getAccountNumber());
		Assert.notNull(account, "Failed to create");
	}

	@Test
	void createCustomer(){
		Customer customer = new Customer("Saurav", "Kumar", "saurav@coreide.com");
		customer = customerService.addCustomer(customer);
		customer = customerService.findCustomerById(customer.getCustomerId());
		Assert.notNull(customer, "Failed to create");
	}

	@Test
	void createCustomerAccount(){
		Customer customer = new Customer("Saurav", "Kumar", "saurav@coreide.com");
		customer = customerService.addCustomer(customer);
		Account account = new Account(AccountType.Savings, 100d);
		account = customerService.addAccount(account, customer.getCustomerId());
		Assert.notNull(account, "Failed to create");
	}

}
