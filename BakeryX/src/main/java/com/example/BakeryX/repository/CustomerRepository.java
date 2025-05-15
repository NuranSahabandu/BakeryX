package com.example.BakeryX.repository;

import com.example.BakeryX.entity.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.*;

@Repository
public class CustomerRepository {

    private final File file = new File("customer.json");
    private final ObjectMapper mapper = new ObjectMapper();


    public List<Customer> getAllCustomers(){
        try {
            if (!file.exists()) return new ArrayList<>();
            return mapper.readValue(file, new TypeReference<List<Customer>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


    public void save(Customer customer) {
        try {
            List<Customer> customers = getAllCustomers(); // Read existing
            int newId = customers.stream()
                    .mapToInt(Customer::getId)
                    .max()
                    .orElse(0) + 1;
            customer.setId(newId);

            customers.add(customer);
            mapper.writeValue(file, customers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //private final List<Customer> list = new ArrayList<Customer>();

    //public List<Customer> getAllCustomers() {
    //    return list;
    //}

    public Customer findById(int id) {
        List<Customer> customers = getAllCustomers();
        for (Customer c : customers) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    //public Customer save(Customer customer) {
      //  Customer c = new Customer();
        //c.setId(customer.getId());
        //c.setName(customer.getName());
        //c.setEmail(customer.getEmail());
        //c.setPassword(customer.getPassword());
        //list.add(c);
        //return c;
    //}

    public Customer updateCustomer(int customerId, Customer updatedcustomer) {
        List<Customer> customers = getAllCustomers();
        for (int i = 0; i < customers.size(); i++){
            Customer existingCustomer = customers.get(i);
            if (existingCustomer.getId() == customerId) {
               existingCustomer.setName(updatedcustomer.getName());
               existingCustomer.setEmail(updatedcustomer.getEmail());
               existingCustomer.setPassword(updatedcustomer.getPassword());
                try {
                    mapper.writeValue(file, customers); // Save the updated list
                    return existingCustomer;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Failed to update customer");
                }
            }
        }
        throw new RuntimeException("Customer not found with id: " + updatedcustomer.getId());
    }

    public String deleteCustomer(int id) {
        List<Customer> customers = getAllCustomers();
        for (int i = 0; i < customers.size(); i++){
            Customer existingCustomer = customers.get(i);
            if (existingCustomer.getId() == id) {
                customers.remove(i);
                try{
                    mapper.writeValue(file, customers);
                    return "Customer deleted";
                }
                catch (Exception e){
                    e.printStackTrace();
                    return "Error deleting customer";
                }
            }

        }
        return "Customer not found";
    }
}
