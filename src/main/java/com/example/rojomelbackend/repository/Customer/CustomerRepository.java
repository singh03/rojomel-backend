package com.example.rojomelbackend.repository.Customer;

import com.example.rojomelbackend.model.entity.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomCustomerRepository {

    Optional<Customer> findByEmail(String email);
}
