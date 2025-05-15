package com.example.BakeryX.controller;

import com.example.BakeryX.entity.Customer;
import com.example.BakeryX.service.CustomerServices;


import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/customer")
public class Controller {

    private final CustomerServices customerServices = new CustomerServices();


    @PostMapping("/create")
    public void createCustomer(@RequestBody Customer customer) {
        customerServices.saveCustomer(customer);
    }

    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerServices.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable int customerId) {
        return customerServices.findById(customerId);
    }

    @PutMapping("update/{customerId}")
    public Customer updateCustomer(@PathVariable int customerId, @RequestBody Customer updatedcustomer) {
        return customerServices.updateCustomer(customerId, updatedcustomer);
    }

    @DeleteMapping("{customerId}")
    public String deleteCustomer(@PathVariable int customerId) {
        return customerServices.deleteCustomer(customerId);
    }



}
