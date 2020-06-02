package com.coreide.services.impl;

import com.coreide.entities.Account;
import com.coreide.entities.Customer;
import com.coreide.exceptions.ResourceNotFoundException;
import com.coreide.services.CustomerService;
import com.coreide.repositories.AccountRepository;
import com.coreide.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Customer addCustomer(Customer customer){
        if(customer != null){
            return customerRepository.save(customer);
        }
        return null;
    }

    @Override
    public Customer findCustomerById(Long id){
        return customerRepository.findByCustomerId(id);
    }

    @Override
    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(Customer customer){
        Customer exCustomer = customerRepository.findByCustomerId(customer.getCustomerId());
        exCustomer.setFirstName(customer.getFirstName());
        exCustomer.setLastName(customer.getLastName());
        exCustomer.setEmail(customer.getEmail());
        return customerRepository.save(exCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId){
        customerRepository.deleteByCustomerId(customerId);
    }

    @Override
    public Account addAccount(Account account, Long customerId) throws ResourceNotFoundException {
        Customer customer = customerRepository.findByCustomerId(customerId);
        if(customer != null){
            account.getCustomers().add(customer);
            Account savedAccount = accountRepository.save(account);
            List<Account> accounts = customer.getAccounts();
            accounts.add(savedAccount);
            customerRepository.save(customer);
            return savedAccount;
        }
        throw new ResourceNotFoundException("Customer doesn't exists");
    }
}
