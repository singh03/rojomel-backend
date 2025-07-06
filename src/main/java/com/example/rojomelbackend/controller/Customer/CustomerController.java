package com.example.rojomelbackend.controller.Customer;

import com.example.rojomelbackend.model.dto.Customer.CustomerDto;
import com.example.rojomelbackend.service.Customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.rojomelbackend.util.Customer.constants.CustomerConstants.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDto dto) {
            if (dto.getId() != null) {
                return ResponseEntity
                        .badRequest()
                        .body(CUSTOMER_ALREADY_EXISTS);
            }
            CustomerDto customerDto = customerService.findCustomerByEmail(dto.getEmail());
            if (customerDto != null){
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(CUSTOMER_ALREADY_EXISTS);
            }

            customerDto = customerService.createCustomer(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(customerDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto dto) {
        CustomerDto customerDto = customerService.getCustomerById(id);
        if (customerDto == null) {
            return ResponseEntity
                    .badRequest()
                    .body(CUSTOMER_NOT_FOUND);
        }
        return ResponseEntity.ok(customerService.updateCustomer(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        CustomerDto customerDto = customerService.getCustomerById(id);
        if (customerDto == null) {
            return ResponseEntity
                    .badRequest()
                    .body(CUSTOMER_NOT_FOUND);
        }
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(CUSTOMER_DELETED);
    }

    @GetMapping("/allCustomers")
    public ResponseEntity<?> getAllCustomers(@RequestParam(required = false, defaultValue = "") String query,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(customerService.getAllCustomers(query, page, size));
    }
}
