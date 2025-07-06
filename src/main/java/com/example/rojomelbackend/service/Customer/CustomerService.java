package com.example.rojomelbackend.service.Customer;

import com.example.rojomelbackend.model.dto.Customer.CustomerDto;
import org.springframework.data.domain.Page;

public interface CustomerService {

    CustomerDto createCustomer(CustomerDto dto);

    CustomerDto updateCustomer(CustomerDto dto);

    void deleteCustomer(Long id);

    CustomerDto getCustomerById(Long id);

    CustomerDto findCustomerByEmail(String email);

    Page<CustomerDto> getAllCustomers(String query, int pageNumber, int pageSize);
}
