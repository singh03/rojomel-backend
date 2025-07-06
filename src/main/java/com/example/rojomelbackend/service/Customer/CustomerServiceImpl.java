package com.example.rojomelbackend.service.Customer;

import com.example.rojomelbackend.mapper.Customer.CustomerMapper;
import com.example.rojomelbackend.model.dto.Customer.CustomerDto;
import com.example.rojomelbackend.model.entity.customer.Customer;
import com.example.rojomelbackend.repository.Customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }


    @Override
    public CustomerDto createCustomer(CustomerDto dto) {
        Customer customer = customerMapper.convert(dto);
        Date date = new Date();
        customer.setDateCreated(date);
        customer.setDateUpdated(date);
        customerRepository.save(customer);
        dto.setDateCreated(date);
        dto.setDateUpdated(date);
        return dto;
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto dto) {

        Customer customer = customerMapper.convert(dto);
        Date date = new Date();
        customer.setDateUpdated(date);
        customerRepository.save(customer);
        dto.setDateUpdated(date);
        return dto;
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.findById(id).ifPresent(customer -> {
            customer.setDeleted(true);
            customerRepository.save(customer);
        });
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        return customerOptional.map(customerMapper::convert).orElse(null);
    }

    @Override
    public CustomerDto findCustomerByEmail(String email) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        return customerOptional.map(customerMapper::convert).orElse(null);
    }

    @Override
    public Page<CustomerDto> getAllCustomers(String query, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Customer> customerPage = customerRepository.getAllCustomers(query, pageRequest);
        return customerPage.map(customerMapper::convert);
    }
}
