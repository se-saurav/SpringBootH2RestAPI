package com.coreide.repositories;

import com.coreide.entities.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByCustomerId(Long customerId);

    void deleteByCustomerId(Long customerId);

    @Override
    List<Customer> findAll();
}