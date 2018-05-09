package com.github.signed.sandboxes.spring.data;

import java.util.List;

import com.github.signed.sandboxes.spring.data.customers.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);
}