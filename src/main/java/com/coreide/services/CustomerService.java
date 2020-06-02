package com.coreide.services;

import com.coreide.exceptions.ResourceNotFoundException;
import com.coreide.entities.Account;
import com.coreide.entities.Customer;

import java.util.List;

public interface CustomerService {

    Customer addCustomer(Customer customer);

    Customer findCustomerById(Long id);

    List<Customer> findAll();

    Customer updateCustomer(Customer customer);

    void deleteCustomer(Long customerId);

    Account addAccount(Account account, Long customerId) throws ResourceNotFoundException;
}
