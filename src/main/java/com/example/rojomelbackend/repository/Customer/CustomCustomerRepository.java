package com.example.rojomelbackend.repository.Customer;

import com.example.rojomelbackend.model.entity.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CustomCustomerRepository {

    Page<Customer> getAllCustomers(String query, PageRequest pageRequest);
}
