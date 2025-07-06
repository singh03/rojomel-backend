package com.example.rojomelbackend.mapper.Customer;

import com.example.rojomelbackend.model.dto.Customer.CustomerDto;
import com.example.rojomelbackend.model.entity.customer.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDto convert(Customer customer){
        return CustomerDto.Builder.customerDto()
                .withId(customer.getId())
                .withName(customer.getName())
                .withEmail(customer.getEmail())
                .withDateCreated(customer.getDateCreated())
                .withDateUpdated(customer.getDateUpdated())
                .withIsDeleted(customer.isDeleted())
                .build();
    }

    public Customer convert(CustomerDto customerDto){
        return Customer.Builder.customer()
                .withName(customerDto.getName())
                .withEmail(customerDto.getEmail())
                .withDeleted(customerDto.isDeleted())
                .withDateCreated(customerDto.getDateCreated())
                .withDateUpdated(customerDto.getDateUpdated())
                .build();
    }
}
