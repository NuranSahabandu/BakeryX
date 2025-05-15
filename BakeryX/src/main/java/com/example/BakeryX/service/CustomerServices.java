package com.example.BakeryX.service;


import com.example.BakeryX.entity.Customer;
import com.example.BakeryX.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CustomerServices {

    private final CustomerRepository customerRepository = new CustomerRepository();

    public void saveCustomer(Customer customer) {
         customerRepository.save(customer);
    }

    public Customer findById(int id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    public Customer updateCustomer(int customerId, Customer updatedcustomer) {
        return customerRepository.updateCustomer(customerId, updatedcustomer);
    }

    public String deleteCustomer(int id) {
        return customerRepository.deleteCustomer(id);
    }

}
