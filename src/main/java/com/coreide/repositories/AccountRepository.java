package com.coreide.repositories;

import com.coreide.entities.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findByAccountNumber(Long accountNumber);

    void deleteByAccountNumber(Long customerId);

    @Override
    List<Account> findAll();
}