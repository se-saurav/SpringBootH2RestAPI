package com.coreide.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue
    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Account> accounts = new ArrayList<>();

    public Customer(){}

    public Customer(String firstName, String lastName, String email) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
