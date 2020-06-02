package com.coreide.controllers;

import com.coreide.entities.Account;
import com.coreide.entities.Customer;
import com.coreide.exceptions.ResourceNotFoundException;
import com.coreide.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @ResponseBody
    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
        customer = customerService.addCustomer(customer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @ResponseBody
    @GetMapping(value = "/{customerId}")
    public Customer getCustomer(@PathVariable(value = "customerId", required = true) Long customerId){
        Customer customer = customerService.findCustomerById(customerId);
        if(customer == null){
            throw new ResourceNotFoundException("Customer doesn't exists");
        }
        return customer;
    }

    @ResponseBody
    @GetMapping
    public List<Customer> getCustomers(){
        List<Customer> customers = customerService.findAll();
        if(customers == null || customers.isEmpty()){
            throw new ResourceNotFoundException("Customer doesn't exists");
        }
        return customers;
    }

    @ResponseBody
    @PutMapping(value = "/{customerId}")
    public Customer updateCustomer(@PathVariable(value = "customerId", required = true) Long customerId, @RequestBody Customer customer){
        customer.setCustomerId(customerId);
        Customer updatedCustomer = customerService.updateCustomer(customer);
        if(updatedCustomer == null){
            throw new ResourceNotFoundException("Customer doesn't exists");
        }
        return updatedCustomer;
    }

    @DeleteMapping(value = "/{customerId}")
    public void deleteCustomer(@PathVariable(value = "customerId", required = true) Long customerId){
        customerService.deleteCustomer(customerId);
    }

    @ResponseBody
    @PostMapping(value = "/{customerId}/accounts")
    public ResponseEntity<Account> addAccount(@RequestBody Account account, @PathVariable(value = "customerId", required = true) Long customerId){
        account = customerService.addAccount(account, customerId);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @ResponseBody
    @GetMapping(value = "/{customerId}/accounts")
    public List<Account> getCustomerAccounts(@PathVariable(value = "customerId", required = true) Long customerId){
        Customer customer = customerService.findCustomerById(customerId);
        if(customer == null){
            throw new ResourceNotFoundException("Customer doesn't exists");
        }
        return customer.getAccounts();
    }

    @ResponseBody
    @GetMapping(value = "/accounts")
    public List<Customer> getAllAccounts(){
        List<Customer> customers = customerService.findAll();
        if(customers == null || customers.isEmpty()){
            throw new ResourceNotFoundException("Customer doesn't exists");
        }
        return customers;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleSecurityException(ResourceNotFoundException ex) {
        return ex.getMessage();
    }

}
